package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.IUpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder
import com.cmoney.backend2.forumocean.service.api.channel.getchannelsarticlebyweight.GetChannelsArticleByWeightRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.update.IUpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
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
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.report.create.ReasonType
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.PersonalArticleType
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import com.google.gson.Gson
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

    override suspend fun getCommentWithId(
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
    ): Result<List<GroupMember>> = withContext(dispatcher.io()) {
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
        reason: ReasonType,
        commentId: Long?
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createReport(
                    path = serverName,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    reasonType = reason.value,
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
}