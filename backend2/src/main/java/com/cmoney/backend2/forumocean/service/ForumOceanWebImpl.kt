package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
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
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.report.create.ReasonType
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
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
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : ForumOceanWeb {

    //region article

    override suspend fun createArticle(body: Content.Article): Result<CreateArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = when (body) {
                    is Content.Article.General -> service.createArticle(
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        requestBody = body
                    )
                    is Content.Article.Group -> service.createArticle(
                        authorization = setting.accessToken.createAuthorizationBearer(),
                        requestBody = body
                    )
                    is Content.Article.Shared -> service.createArticle(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = body
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getArticle(articleId: Long): Result<ArticleResponseBody.GeneralArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getQuestionArticle(articleId: Long): Result<ArticleResponseBody.QuestionArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getQuestionArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getGroupArticle(articleId: Long): Result<ArticleResponseBody.GroupArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSharedArticle(articleId: Long): Result<ArticleResponseBody.SharedArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSharedArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSignalArticle(articleId: Long): Result<ArticleResponseBody.SignalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSignalArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getNewsArticle(articleId: Long): Result<ArticleResponseBody.NewsArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getNewsArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getUnknownArticle(articleId: Long): Result<ArticleResponseBody.UnknownArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUnknownArticle(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    //endregion

    override suspend fun getMemberStatistics(memberIdList: List<Long>) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMemberStatistics(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteCollection(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteCollection(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createArticleInterest(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleInterest(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createArticleDonate(articleId: Long, donateValue: Int): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleDonate(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    donateValue = donateValue
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getArticleDonate(articleId: Long,offset: Int,fetch: Int): Result<List<DonateInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getArticleDonate(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getGroup(groupId: Long): Result<GroupResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getUserOwnGroup(ownId: Long,offset: Int,fetch: Int): Result<List<GroupResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUserOwnGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    ownerId = ownId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getMemberManagedGroups(managerId: Long,offset: Int,fetch: Int): Result<List<GroupResponseBody>> =
            withContext(dispatcher.io()){
                kotlin.runCatching {
                    service.getMemberManagedGroups(
                            authorization = setting.accessToken.createAuthorizationBearer(),
                            managerId = managerId,
                            offset = offset,
                            fetch = fetch
                    ).checkResponseBody(jsonParser)
                }
            }

    override suspend fun getMemberBelongGroups(memberId: Long,offset: Int,fetch: Int): Result<List<GroupResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberBelongGroups(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getMemberJoinAnyGroups(memberId: Long): Result<GetMemberJoinAnyGroupsResponseBody> =
            withContext(dispatcher.io()) {
                kotlin.runCatching {
                    service.getMemberJoinAnyGroups(
                            authorization = setting.accessToken.createAuthorizationBearer(),
                            memberId = memberId
                    ).checkResponseBody(jsonParser)
                }
            }

    override suspend fun createGroup(groupName: String): Result<CreateGroupResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupName = groupName
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun updateGroup(groupId: Long, body: UpdateGroupRequestBody): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateGroup(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteGroup(groupId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteGroup(
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun join(groupId: Long, reason: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.join(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    reason = reason
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getMembers(
        groupId: Long,
        offset: Int,
        fetch: Int,
        includeManagerInfo: Boolean
    ): Result<List<GroupMember>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMembers(
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId,
                offset = offset,
                fetch = fetch,
                includeManagerInfo = includeManagerInfo
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getApprovals(groupId: Long,offset: Int,fetch: Int): Result<List<GroupPendingApproval>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getApprovals(
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
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId,
                memberId = memberId,
                position = position.value
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun kick(groupId: Long, memberId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.kick(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun leave(groupId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.leave(
                authorization = setting.accessToken.createAuthorizationBearer(),
                groupId = groupId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun pinArticle(articleId: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.pinArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unpinArticle(articleId: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unpinArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                articleId = articleId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getOfficials(officialIds: List<Long>): Result<List<OfficialChannelInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficials(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    officialIds = officialIds.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getOfficialSubscribedCount(officialId: Long): Result<GetOfficialSubscribedCountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficialSubscribedCount(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    officialId = officialId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSubscribedCount(memberId: Long): Result<GetSubscribedCountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSubscribedCount(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSubscribed(memberId: Long,offset: Int,fetch: Int): Result<List<Int>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSubscribed(
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
                authorization = setting.accessToken.createAuthorizationBearer(),
                officialId = officialId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unsubscribe(officialId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.unsubscribe(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    officialId = officialId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun unsubscribeAll(): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unsubscribeAll(
                authorization = setting.accessToken.createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getFollowingList(memberId: Long,offset : Int,fetch : Int): Result<List<Long>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getFollowingList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberId = memberId,
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getFollowers(memberId: Long,offset : Int,fetch : Int): Result<List<Long>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getFollowers(
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
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unfollow(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unfollow(
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun block(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.block(
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unblock(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unblock(
                authorization = setting.accessToken.createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getBlockingList(offset : Int,fetch : Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBlockingList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch

            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getBlockers(offset : Int,fetch : Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBlockers(
                authorization = setting.accessToken.createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getRelationshipWithMe(memberIdList: List<Long>) = withContext(dispatcher.io()){
        kotlin.runCatching {
            service.getRelationshipWithMe(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    channelIds = channelIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getChannelIds(memberIdList: List<Long>): Result<List<ChannelIdAndMemberId>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getChannelIds(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    memberIds = memberIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun createVote(articleId: Long, optionIndex: Int): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createVote(
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
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }
}