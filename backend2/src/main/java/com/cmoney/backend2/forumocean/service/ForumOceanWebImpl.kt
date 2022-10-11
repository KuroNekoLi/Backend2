package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.IUpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder
import com.cmoney.backend2.forumocean.service.api.channel.getchannelsarticlebyweight.GetChannelsArticleByWeightRequestBody
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.hide.HideCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.update.IUpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.group.v2.Admins
import com.cmoney.backend2.forumocean.service.api.group.v2.Approval
import com.cmoney.backend2.forumocean.service.api.group.v2.AvailableBoardIds
import com.cmoney.backend2.forumocean.service.api.group.v2.Board
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardManipulation
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardSingle
import com.cmoney.backend2.forumocean.service.api.group.v2.Group
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupManipulation
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember2
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupPushSettingRequest
import com.cmoney.backend2.forumocean.service.api.group.v2.JoinGroupRequest
import com.cmoney.backend2.forumocean.service.api.group.v2.MemberRoles
import com.cmoney.backend2.forumocean.service.api.group.v2.PendingRequests
import com.cmoney.backend2.forumocean.service.api.group.v2.PushType
import com.cmoney.backend2.forumocean.service.api.notify.get.GetNotifyResponseBody
import com.cmoney.backend2.forumocean.service.api.notify.getcount.GetNotifyCountResponseBody
import com.cmoney.backend2.forumocean.service.api.notifysetting.NotifyPushSetting
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getcommodityrank.GetCommodityRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getexpertmemberrank.GetExpertMemberRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getfansmemberrank.FansMemberRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getsolutionexpertrank.SolutionExpertRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rating.MemberRatingCounter
import com.cmoney.backend2.forumocean.service.api.rating.OthersRatingComment
import com.cmoney.backend2.forumocean.service.api.rating.RatingComment
import com.cmoney.backend2.forumocean.service.api.rating.ReviewRequest
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.role.Role
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.PersonalArticleType
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.GetCommentsResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.withContext

class ForumOceanWebImpl(
    private val service: ForumOceanService,
    private val setting: Setting,
    private val jsonParser: Gson,
    private val serverName: String = "",
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : ForumOceanWeb {

    override suspend fun getBanState(): Result<GetBanStateResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBanState(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun createPersonalArticle(body: Content.PersonalArticle): Result<CreatePersonalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = when (body) {
                    is Content.PersonalArticle.Columnist -> service.createPersonalArticle(
                        path = serverName,
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        articleType = PersonalArticleType.COLUMNIST.articleType,
                        requestBody = body
                    )

                    is Content.PersonalArticle.Note -> service.createPersonalArticle(
                        path = serverName,
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        articleType = PersonalArticleType.NOTE.articleType,
                        requestBody = body
                    )
                }
                response.checkResponseBody(jsonParser)
            }
        }

    override suspend fun createArticle(body: Content.Article): Result<CreateArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = when (body) {
                    is Content.Article.General -> service.createArticle(
                        path = serverName,
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        requestBody = body
                    )

                    is Content.Article.Group -> service.createArticle(
                        path = serverName,
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        requestBody = body
                    )

                    is Content.Article.Shared -> service.createArticle(
                        path = serverName,
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        requestBody = body
                    )

                    is Content.Article.Column -> service.createArticle(
                        path = serverName,
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        requestBody = body
                    )
                }
                response.checkResponseBody(jsonParser)
            }
        }

    override suspend fun createQuestion(body: Content.Question): Result<CreateQuestionResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createQuestion(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = body
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getArticleV2")
    override suspend fun getArticle(articleId: Long): Result<ArticleResponseBody.GeneralArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getQuestionArticleV2")
    override suspend fun getQuestionArticle(articleId: Long): Result<ArticleResponseBody.QuestionArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getQuestionArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getGroupArticleV2")
    override suspend fun getGroupArticle(articleId: Long): Result<ArticleResponseBody.GroupArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getSharedArticleV2")
    override suspend fun getSharedArticle(articleId: Long): Result<ArticleResponseBody.SharedArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSharedArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getSignalArticleV2")
    override suspend fun getSignalArticle(articleId: Long): Result<ArticleResponseBody.SignalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSignalArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getNewsArticleV2")
    override suspend fun getNewsArticle(articleId: Long): Result<ArticleResponseBody.NewsArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getNewsArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getPersonalArticleV2")
    override suspend fun getPersonalArticle(articleId: Long): Result<ArticleResponseBody.PersonalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPersonalArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("請使用getUnknownArticleV2")
    override suspend fun getUnknownArticle(articleId: Long): Result<ArticleResponseBody.UnknownArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUnknownArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getArticleV2(articleId: Long): Result<ArticleResponseBody.GeneralArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getQuestionArticleV2(articleId: Long): Result<ArticleResponseBody.QuestionArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getQuestionArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getGroupArticleV2(articleId: Long): Result<ArticleResponseBody.GroupArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSharedArticleV2(articleId: Long): Result<ArticleResponseBody.SharedArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSharedArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSignalArticleV2(articleId: Long): Result<ArticleResponseBody.SignalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSignalArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getNewsArticleV2(articleId: Long): Result<ArticleResponseBody.NewsArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getNewsArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getPersonalArticleV2(articleId: Long): Result<ArticleResponseBody.PersonalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPersonalArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getUnknownArticleV2(articleId: Long): Result<ArticleResponseBody.UnknownArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUnknownArticleV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun updateArticle(
        articleId: Long,
        updateHelper: IUpdateArticleHelper
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    body = updateHelper.create()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteArticle(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    //endregion

    override suspend fun getMemberStatistics(memberIdList: List<Long>) =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberStatistics(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        weight: Long,
        count: Int
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getChannelsArticleByWeight(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                channelNameList = GetChannelsArticleByWeightRequestBody(channelNameBuilderList.map { it.create() }),
                startScore = weight,
                count = count
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        count: Int
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getChannelsArticleByWeight(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                channelNameList = GetChannelsArticleByWeightRequestBody(channelNameBuilderList.map { it.create() }),
                count = count
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun createCollection(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createCollection(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteCollection(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteCollection(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    @Deprecated("請使用createCommentV2")
    override suspend fun createComment(
        articleId: Long,
        text: String?,
        multiMedia: List<MediaType>?,
        position: Any?
    ): Result<CreateCommentResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createComment(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId, body = CreateCommentRequestBody(
                    text = text,
                    multiMedia = multiMedia,
                    position = position
                )
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun createCommentV2(
        articleId: String,
        text: String?,
        multiMedia: List<MediaType>?
    ): Result<CreateCommentResponseBodyV2> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createCommentV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    body = CreateCommentRequestBodyV2(
                        text = text,
                        multiMedia = multiMedia
                    )
                ).checkResponseBody(jsonParser)
            }
        }
    }

    @Deprecated("請使用createCommentV2")
    override suspend fun createGroupArticleComment(
        articleId: Long,
        text: String?,
        multiMedia: List<MediaType>?,
        position: Any?
    ): Result<CreateCommentResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createGroupArticleComment(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId, body = CreateCommentRequestBody(
                    text = text,
                    multiMedia = multiMedia,
                    position = position
                )
            ).checkResponseBody(jsonParser)
        }
    }

    @Deprecated("請使用getCommentV2")
    override suspend fun getComment(
        articleId: Long,
        commentId: Long?,
        offsetCount: Int?
    ): Result<List<CommentResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getComment(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId,
                commentId = commentId,
                offsetCount = offsetCount
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getCommentV2(
        articleId: String,
        startCommentIndex: Long?,
        fetch: Int?
    ): Result<GetCommentsResponseBody> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCommentV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    startCommentIndex = startCommentIndex,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    @Deprecated("請使用getCommentsByIndex")
    override suspend fun getCommentsWithId(
        articleId: Long,
        commentIds: List<Long>
    ): Result<List<CommentResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommentWithId(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId,
                commentIds = commentIds.joinToString(",")
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getCommentsByIndex(
        articleOrCommentId: String,
        commentIndices: List<Long>
    ): Result<GetCommentsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommentsByIndex(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleOrCommentId,
                commentIndex = commentIndices.joinToString(",")
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getGroupManagerComments(
        articleId: Long
    ): Result<List<CommentResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getGroupManagerComments(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun updateComment(
        articleId: Long,
        commentId: Long,
        helper: IUpdateCommentHelper
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateComment(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    commentId = commentId,
                    body = helper.create()
                ).handleNoContent(jsonParser)
            }
        }


    override suspend fun deleteComment(articleId: Long, commentIndex: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteComment(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    commentIndex = commentIndex
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun reactionComment(
        articleId: Long,
        commentIndex: Long,
        reactionType: ReactionType
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.reactComment(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId,
                commentIndex = commentIndex,
                reactionType = reactionType.value
            ).handleNoContent(jsonParser)
        }
    }


    override suspend fun getReactionDetail(
        articleId: Long,
        commentIndex: Long,
        reactions: List<ReactionType>,
        skipCount: Int,
        takeCount: Int
    ): Result<List<ReactionInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getReactionDetail(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId,
                commentIndex = commentIndex,
                reactions = reactions.joinToString { it.value.toString() },
                skipCount = skipCount,
                takeCount = takeCount
            ).checkResponseBody(jsonParser)
        }
    }


    override suspend fun removeReactionComment(
        articleId: Long,
        commentIndex: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.removeCommentReaction(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId,
                commentIndex = commentIndex
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun createArticleReaction(articleId: Long, type: ReactionType): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleReaction(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    reactionType = type.value
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getArticleReactionDetail(
        articleId: Long,
        reactionTypeList: List<ReactionType>,
        skipCount: Int,
        count: Int
    ): Result<List<ReactionInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getArticleReactionDetail(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId,
                reactions = reactionTypeList.joinToString { it.value.toString() },
                skipCount = skipCount,
                count = count
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun deleteArticleReaction(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteArticleReaction(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createArticleInterest(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleInterest(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createArticleDonate(articleId: Long, donateValue: Int): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleDonate(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    donateValue = donateValue
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getArticleDonate(
        articleId: Long,
        offset: Int,
        fetch: Int
    ): Result<List<DonateInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getArticleDonate(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getNotify(
        updateTime: Long,
        offsetCount: Int
    ): Result<List<GetNotifyResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getNotify(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    updateTime = updateTime,
                    offsetCount = offsetCount
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getNotifyCount(): Result<GetNotifyCountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCount(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun resetNotifyCount(): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.resetCount(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun setNotifyRead(
        notifyType: String,
        mergeKey: String,
        isNew: Boolean
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.setRead(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    notifyType = notifyType,
                    mergeKey = mergeKey,
                    isNew = isNew
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getPushDefaultSetting(): Result<List<NotifyPushSetting>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPushDefaultSetting(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getUserNotifySetting(): Result<List<NotifyPushSetting>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUserNotifySetting(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun setNotifySetting(
        notifyType: String,
        subType: String,
        enable: Boolean
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.setNotifySetting(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    notifyType = notifyType,
                    subType = subType,
                    enable = enable
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getGroup(groupId: Long): Result<GroupResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getGroupsByKeyword(
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<GroupResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupsByKeyword(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    keyword = keyword,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getGroupsByPosition(
        ownId: Long,
        offset: Int,
        fetch: Int,
        positions: List<GroupPosition>,
        includeAppGroup: Boolean
    ): Result<List<GroupResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupsWithPosition(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = ownId,
                    offset = offset,
                    fetch = fetch,
                    includeAppGroup = includeAppGroup,
                    position = positions.map { it.position }.sum()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getMemberManagedGroups(
        memberId: Long,
        offset: Int,
        fetch: Int,
        includeAppGroup: Boolean
    ): Result<List<GroupResponseBody>> = getGroupsByPosition(
        memberId,
        offset,
        fetch,
        listOf(GroupPosition.MANAGEMENT),
        includeAppGroup
    )

    override suspend fun getMemberBelongGroups(
        memberId: Long,
        offset: Int,
        fetch: Int,
        includeAppGroup: Boolean
    ): Result<List<GroupResponseBody>> = getGroupsByPosition(
        memberId,
        offset,
        fetch,
        listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT),
        includeAppGroup
    )

    override suspend fun getMemberJoinAnyGroups(memberId: Long): Result<GetMemberJoinAnyGroupsResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberJoinAnyGroups(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun createGroup(groupName: String): Result<CreateGroupResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupName = groupName
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun updateGroup(groupId: Long, body: UpdateGroupRequestBody): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    body = body
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun transferGroup(groupId: Long, memberId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.transferGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteGroup(groupId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteGroup(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun join(groupId: Long, reason: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                if (reason.isEmpty()) {
                    error("reason不能為空字串")
                }
                service.join(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    reason = reason
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun join(groupId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.join(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    reason = null
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getMembers(
        groupId: Long,
        offset: Int,
        fetch: Int,
        position: List<GroupPosition>
    ): Result<List<com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMembers(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    offset = offset,
                    fetch = fetch,
                    position = position.map { it.position }.sum()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getApprovals(
        groupId: Long,
        offset: Int,
        fetch: Int
    ): Result<List<GroupPendingApproval>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getApprovals(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun approval(groupId: Long, memberId: Long, isAgree: Boolean): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.approval(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId,
                    isAgree = isAgree
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun changeGroupMemberPosition(
        groupId: Long,
        memberId: Long,
        position: GroupPosition
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.changeGroupMemberPosition(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId,
                memberId = memberId,
                position = position.position
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun kick(groupId: Long, memberId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.kick(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun leave(groupId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.leave(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun pinArticle(articleId: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.pinArticle(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unpinArticle(articleId: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unpinArticle(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getOfficials(offset: Int, fetch: Int): Result<List<OfficialChannelInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficials(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getOfficialsByIds(officialIds: List<Long>): Result<List<OfficialChannelInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficialsByIds(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    officialIds = officialIds.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getOfficialsByKeyWord(
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<OfficialChannelInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficialsByKeyword(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    keyword = keyword,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getOfficialSubscribedCount(officialId: Long): Result<GetOfficialSubscribedCountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficialSubscribedCount(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    officialId = officialId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSubscribedCount(memberId: Long): Result<GetSubscribedCountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSubscribedCount(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSubscribed(memberId: Long, offset: Int, fetch: Int): Result<List<Int>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSubscribed(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun subscribe(officialId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.subscribe(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                officialId = officialId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unsubscribe(officialId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.unsubscribe(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    officialId = officialId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun unsubscribeAll(): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unsubscribeAll(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getCommodityRank(
        offset: Int,
        fetch: Int
    ): Result<List<GetCommodityRankResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommodityRank(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getUSCommodityRank(
        offset: Int,
        fetch: Int
    ): Result<List<GetCommodityRankResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getUSCommodityRank(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getExpertMemberRank(
        offset: Int,
        fetch: Int
    ): Result<List<GetExpertMemberRankResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getExpertMemberRank(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }


    override suspend fun getSpecificExpertMemberRank(memberIds: String): Result<List<GetExpertMemberRankResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSpecificExpertMemberRank(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIds
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getMemberFansRank(
        offset: Int,
        fetch: Int
    ): Result<List<FansMemberRankResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMemberFansRank(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getSpecificMemberFansRank(memberIds: String): Result<List<FansMemberRankResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSpecificMemberFansRank(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIds
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSolutionExpertRank(
        offset: Int,
        fetch: Int
    ): Result<List<SolutionExpertRankResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getSolutionExpertRank(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getSpecificSolutionExpertRank(memberIds: String): Result<List<SolutionExpertRankResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSpecificSolutionExpertRank(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIds
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getFollowingList(
        memberId: Long,
        offset: Int,
        fetch: Int
    ): Result<List<Long>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getFollowingList(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getFollowers(memberId: Long, offset: Int, fetch: Int): Result<List<Long>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getFollowers(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun follow(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.follow(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unfollow(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unfollow(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun block(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.block(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unblock(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unblock(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getBlockingList(offset: Int, fetch: Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBlockingList(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch

            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getBlockers(offset: Int, fetch: Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBlockers(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getRelationshipWithMe(memberIdList: List<Long>) =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getRelationshipWithMe(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIdList.joinToString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun createReport(
        articleId: Long,
        reasonType: Int,
        commentId: Long?
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createReport(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    reasonType = reasonType,
                    commentId = commentId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteReport(articleId: Long, commentId: Long?): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteReport(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    commentId = commentId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getMemberIds(channelIdList: List<Long>): Result<List<ChannelIdAndMemberId>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberIds(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    channelIds = channelIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getChannelIds(memberIdList: List<Long>): Result<List<ChannelIdAndMemberId>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getChannelIds(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun searchMembers(
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<SearchMembersResponseBody>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.searchMembers(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    keyword = keyword,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun createVote(articleId: Long, optionIndex: Int): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createVote(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    optionIndex = optionIndex
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getCurrentVote(articleId: Long): Result<List<VoteInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCurrentVote(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun exchangeColumnArticle(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.exchangeColumnArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                )
                if (response.code() >= 400) {
                    throw response.parseServerException(jsonParser)
                }
                Unit
            }
        }

    override suspend fun getRole(): Result<Set<Role>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getRole(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer()
            ).checkResponseBody(jsonParser)

            val roles = Role.values()
            return@runCatching response.mapNotNull { apiValue ->
                roles.firstOrNull { it.value == apiValue }
            }.toSet()
        }
    }

    override suspend fun getMembersByRole(roleId: Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getMembersByRoleId(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                roleId = roleId
            ).checkResponseBody(jsonParser)
            response.memberIds ?: listOf()
        }
    }

    override suspend fun getRole(memberId: Long): Result<Set<Role>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getRole(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).checkResponseBody(jsonParser)
            val roles = Role.values()
            return@runCatching response.mapNotNull { apiValue ->
                roles.firstOrNull { it.value == apiValue }
            }.toSet()
        }
    }

    override suspend fun getExchangeCount(memberId: Long): Result<ExchangeCount> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                return@runCatching service.getExchangeCount(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun isMemberSubscribe(memberId: Long): Result<Boolean> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.isMemberSubscribe(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId
                )
                if (response.code() == 200) {
                    response.body()?.string()?.trim() == "true"
                } else {
                    throw ServerException(response.code(), "")
                }
            }
        }

    override suspend fun getStockReportId(
        date: String,
        brokerId: String,
        stockId: String
    ): Result<Int> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            return@runCatching service.getStockReportId(
                path = serverName,
                authorization = setting.accessToken.createAuthorizationBearer(),
                date = date,
                brokerId = brokerId,
                stockId = stockId
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getColumnistVipGroup(columnistMemberId: Long): Result<GetColumnistVipGroupResponse> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getColumnistVipGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    columnistMemberId = columnistMemberId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getGroupV2(groupId: Long): Result<Group> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getGroupByRoles(
        memberId: Long?,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>
    ): Result<List<Group>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupsByRole(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    roles = roles.joinToString(",") { it.value },
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun createGroup(group: GroupManipulation): Result<Long> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = group
                ).checkResponseBody(jsonParser).id?.toLong() ?: 0L
            }
        }
    }

    override suspend fun updateGroup(groupId: Long, group: GroupManipulation): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    body = group
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun dismissGroup(groupId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteGroupV2(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun createGroupBoard(
        groupId: Long,
        board: BoardManipulation
    ): Result<Long> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroupBoard(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    body = board
                ).checkResponseBody(jsonParser).id?.toLong() ?: 0L
            }
        }
    }

    override suspend fun updateGroupBoard(
        boardId: Long,
        board: BoardManipulation
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateGroupBoard(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    boardId = boardId,
                    body = board
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getGroupBoards(groupId: Long): Result<List<Board>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupBoards(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getGroupBoard(boardId: Long): Result<BoardSingle> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupBoard(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    boardId = boardId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun deleteGroupBoard(boardId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteGroupBoard(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    boardId = boardId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun hasNewGroupPending(groupId: Long): Result<Boolean> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.hasNewGroupPending(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                )
                if (response.code() == 200) {
                    JsonParser.parseString(
                        response.body()?.string() ?: "{}"
                    ).asJsonObject.get("hasNewPending").asBoolean
                } else {
                    throw ServerException(response.code(), "")
                }
            }
        }
    }

    override suspend fun getGroupMemberRoles(
        groupId: Long,
        memberId: Long
    ): Result<MemberRoles> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupMemberRoles(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun updateGroupMemberRoles(
        groupId: Long,
        memberId: Long,
        roleIds: List<Int>
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateGroupMemberRoles(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId,
                    roles = roleIds
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getGroupMembers(
        groupId: Long,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>,
        offset: Int,
        fetch: Int
    ): Result<List<GroupMember2>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupMembers(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    roles = roles.joinToString(",") { it.value },
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun leaveGroup(groupId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.leaveGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getGroupAdmins(groupId: Long): Result<Admins> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupAdmins(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun searchGroupMember(
        groupId: Long,
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.searchGroupMember(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    keyword = keyword,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun joinGroup(
        groupId: Long,
        joinGroupRequest: JoinGroupRequest
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.joinGroup(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    body = joinGroupRequest
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getGroupPendingRequests(
        groupId: Long,
        timestamp: Long,
        fetch: Int
    ): Result<PendingRequests> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupPendingRequests(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    timestamp = timestamp,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun searchGroupPendingRequests(
        groupId: Long,
        keyword: String,
        timestamp: Long,
        fetch: Int
    ): Result<PendingRequests> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.searchGroupPendingRequests(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    keyword = keyword,
                    timestamp = timestamp,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun approvalGroupRequest(
        groupId: Long,
        approval: List<Approval>
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.approvalGroupRequest(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    body = approval
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun kickGroupMember(groupId: Long, memberId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.kickGroupMember(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun createGroupArticle(
        boardId: Long,
        content: Content.Article.General
    ): Result<CreateArticleResponseBody> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroupArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    boardId = boardId,
                    requestBody = content
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun deleteGroupArticle(articleId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteGroupArticle(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun deleteGroupArticleComment(articleId: Long, commentId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteGroupArticleComment(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    commentId = commentId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getAvailableBoardIds(): Result<AvailableBoardIds> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getAvailableBoardIds(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getGroupPushSetting(groupId: Long): Result<PushType> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val result = service.getGroupPushSetting(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
                when (result.pushType) {
                    "all" -> PushType.ALL
                    "admin" -> PushType.ADMIN
                    "none" -> PushType.NONE
                    else -> PushType.NONE
                }
            }
        }
    }

    override suspend fun setGroupPushSetting(groupId: Long, pushType: PushType): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.setGroupPushSetting(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GroupPushSettingRequest(
                        groupId,
                        when (pushType) {
                            PushType.ALL -> "all"
                            PushType.ADMIN -> "admin"
                            PushType.NONE -> "none"
                        }
                    )
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getMemberRatingCounter(memberId: Long): Result<MemberRatingCounter> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberRatingCounter(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getRatingComment(creatorId: Long, memberId: Long): Result<RatingComment> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getRatingComment(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    creatorId = creatorId,
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getMemberRatingComments(
        memberId: Long,
        offset: Int,
        fetch: Int,
        sortType: SortType
    ): Result<List<OthersRatingComment>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberRatingComments(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId,
                    sortType = sortType.value,
                    skipCount = offset,
                    fetchCount = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun reviewUser(request: ReviewRequest): Result<String> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.reviewUser(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = request
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getColumnistAll(): Result<List<Long>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getColumnistAll(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(jsonParser).mapNotNull {
                    it.memberId
                }
            }
        }
    }

    override suspend fun hideComment(articleId: String): Result<HideCommentResponseBody> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.hideComment(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getSingleComment(commentId: String): Result<CommentResponseBodyV2> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSingleComment(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = commentId
                ).checkResponseBody(jsonParser)
            }
        }
    }
}
