package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.IUpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder
import com.cmoney.backend2.forumocean.service.api.channel.getchannelsarticlebyweight.GetChannelsArticleByWeightRequestBody
import com.cmoney.backend2.forumocean.service.api.chatroom.GetUncheckChatRoomCountResponse
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.hide.HideCommentRequestBody
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
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupBoardPushSettingRequestBody
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
import com.cmoney.backend2.forumocean.service.api.report.ReportRequestBody
import com.cmoney.backend2.forumocean.service.api.role.Role
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat.GetAllChatRoomResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat.GetGroupBoardArticlesResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.promoted.GetPromotedArticlesResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.promoted.PromotedArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.recommendations.GetRecommendationResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.spacepin.GetSpaceBoardPinArticlesResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.GetCommentsResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.MemberEmojis
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.withContext

class ForumOceanWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: ForumOceanService,
    private val jsonParser: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ForumOceanWeb {

    private val serverName = ""

    override suspend fun getBanState(
        domain: String,
        url: String
    ): Result<GetBanStateResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBanState(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun createPersonalArticle(
        body: Content.PersonalArticle,
        domain: String,
        url: String
    ): Result<CreatePersonalArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = when (body) {
                    is Content.PersonalArticle.Columnist -> service.createPersonalArticle(
                        url = url,
                        authorization = manager.getAccessToken().createAuthorizationBearer(),
                        requestBody = body
                    )

                    is Content.PersonalArticle.Note -> service.createPersonalArticle(
                        url = url,
                        authorization = manager.getAccessToken().createAuthorizationBearer(),
                        requestBody = body
                    )
                }
                response.checkResponseBody(jsonParser)
            }
        }

    override suspend fun createArticle(
        body: Content.Article,
        domain: String,
        url: String
    ): Result<CreateArticleResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = when (body) {
                    is Content.Article.General -> service.createArticle(
                        url = url,
                        authorization = manager.getAccessToken().createAuthorizationBearer(),
                        requestBody = body
                    )
                    is Content.Article.Group -> service.createArticle(
                        url = url,
                        authorization = manager.getAccessToken().createAuthorizationBearer(),
                        requestBody = body
                    )
                    is Content.Article.Shared -> service.createArticle(
                        url = url,
                        authorization = manager.getAccessToken().createAuthorizationBearer(),
                        requestBody = body
                    )
                    is Content.Article.Column -> service.createArticle(
                        url = url,
                        authorization = manager.getAccessToken().createAuthorizationBearer(),
                        requestBody = body
                    )
                }
                response.checkResponseBody(jsonParser)
            }
        }

    override suspend fun createQuestion(
        body: Content.Question,
        domain: String,
        url: String
    ): Result<CreateQuestionResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createQuestion(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = body
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getArticleV2(articleId: Long): Result<ArticleResponseBodyV2> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getArticleV2(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId.toString()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun updateArticle(
        articleId: Long,
        updateHelper: IUpdateArticleHelper,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateArticle(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = updateHelper.create()
                ).handleNoContent(jsonParser)
            }
        }

    @Deprecated("待服務實作完成，使用deleteArticleV2")
    override suspend fun deleteArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteArticle(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteArticleV2(
        articleId: String,
        domain: String,
        url: String
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteArticleV2(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }
    }


    //endregion

    override suspend fun getMemberStatistics(
        memberIdList: List<Long>,
        domain: String,
        url: String
    ) = withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberStatistics(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    memberIds = memberIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        weight: Long?,
        count: Int,
        domain: String,
        url: String
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getChannelsArticleByWeight(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                channelNameList = GetChannelsArticleByWeightRequestBody(channelNameBuilderList.map { it.create() }),
                startScore = weight,
                count = count
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun createCollection(
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createCollection(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteCollection(
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteCollection(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createCommentV2(
        id: String,
        text: String?,
        multiMedia: List<MediaType>?
    ): Result<CreateCommentResponseBodyV2> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createCommentV2(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = id,
                    body = CreateCommentRequestBodyV2(
                        text = text,
                        multiMedia = multiMedia
                    )
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getCommentV2(
        articleId: String,
        startCommentIndex: Long?,
        fetch: Int?,
        domain: String,
        url: String
    ): Result<GetCommentsResponseBody> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCommentV2(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    startCommentIndex = startCommentIndex,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getCommentsByIndex(
        id: String,
        commentIndices: List<Long>,
        domain: String,
        url: String
    ): Result<List<CommentResponseBodyV2>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommentsByIndex(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                articleId = id,
                commentIndex = commentIndices.joinToString(",")
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getGroupManagerComments(
        articleId: Long,
        domain: String,
        url: String
    ): Result<List<CommentResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getGroupManagerComments(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun updateComment(
        articleId: Long,
        commentId: Long,
        helper: IUpdateCommentHelper,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateComment(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = helper.create()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteCommentV2(
        commentId: String,
        domain: String,
        url: String
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteCommentV2(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }
    }

    @Deprecated("請使用getReactionDetailV2")
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                articleId = articleId,
                commentIndex = commentIndex,
                reactions = reactions.joinToString { it.value.toString() },
                skipCount = skipCount,
                takeCount = takeCount
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getReactionDetailV2(
        id: String,
        reactions: List<ReactionType>,
        offset: Int,
        fetch: Int
    ): Result<MemberEmojis> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getReactionDetailV2(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = id,
                    emojiTypes = reactions.joinToString { it.value.toString() },
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }


    @Deprecated("請使用deleteReaction")
    override suspend fun removeReactionComment(
        articleId: Long,
        commentIndex: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.removeCommentReaction(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                articleId = articleId,
                commentIndex = commentIndex
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun createReaction(
        id: String,
        type: ReactionType,
        domain: String,
        url: String
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createReaction(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }
    }

    @Deprecated("請使用getReactionDetailV2")
    override suspend fun getArticleReactionDetail(
        articleId: Long,
        reactionTypeList: List<ReactionType>,
        skipCount: Int,
        count: Int
    ): Result<List<ReactionInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getArticleReactionDetail(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                articleId = articleId,
                reactions = reactionTypeList.joinToString { it.value.toString() },
                skipCount = skipCount,
                count = count
            ).checkResponseBody(jsonParser)
        }
    }

    @Deprecated("請使用deleteReaction")
    override suspend fun deleteArticleReaction(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteArticleReaction(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteReaction(id: String): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteReaction(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = id
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun createArticleInterest(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleInterest(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createArticleDonate(articleId: Long, donateValue: Int): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleDonate(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun resetNotifyCount(): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.resetCount(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getUserNotifySetting(): Result<List<NotifyPushSetting>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUserNotifySetting(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun createGroup(groupName: String): Result<CreateGroupResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroup(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    groupName = groupName
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun updateGroup(groupId: Long, body: UpdateGroupRequestBody): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateGroup(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteGroup(groupId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteGroup(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    groupId = groupId,
                    memberId = memberId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun leave(groupId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.leave(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                groupId = groupId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun pinArticle(articleId: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.pinArticle(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                articleId = articleId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unpinArticle(articleId: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unpinArticle(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                articleId = articleId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getOfficials(offset: Int, fetch: Int): Result<List<OfficialChannelInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getOfficials(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    officialId = officialId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSubscribedCount(memberId: Long): Result<GetSubscribedCountResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSubscribedCount(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getSubscribed(memberId: Long, offset: Int, fetch: Int): Result<List<Int>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSubscribed(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                officialId = officialId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unsubscribe(officialId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.unsubscribe(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    officialId = officialId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun unsubscribeAll(): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unsubscribeAll(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer()
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unfollow(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unfollow(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun block(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.block(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unblock(memberId: Long): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.unblock(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getBlockingList(offset: Int, fetch: Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBlockingList(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch

            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getBlockers(offset: Int, fetch: Int) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getBlockers(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    memberIds = memberIdList.joinToString()
                ).checkResponseBody(jsonParser)
            }
        }

    @Deprecated("檢舉留言請使用createReportV2，檢舉主文仍暫時使用這個，待服務實作後遷移")
    override suspend fun createReport(
        articleId: Long,
        reasonType: Int,
        commentId: Long?
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createReport(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId,
                    reasonType = reasonType,
                    commentId = commentId
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun createReportV2(
        id: String,
        reasonType: Int
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createReportV2(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = id,
                    body = ReportRequestBody(reasonType)
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun deleteReport(articleId: Long, commentId: Long?): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteReport(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    channelIds = channelIdList.joinToString(",")
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getChannelIds(memberIdList: List<Long>): Result<List<ChannelIdAndMemberId>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getChannelIds(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun exchangeColumnArticle(articleId: Long): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.exchangeColumnArticle(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer()
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                roleId = roleId
            ).checkResponseBody(jsonParser)
            response.memberIds ?: listOf()
        }
    }

    override suspend fun getRole(memberId: Long): Result<Set<Role>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getRole(
                path = serverName,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun isMemberSubscribe(memberId: Long): Result<Boolean> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.isMemberSubscribe(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    groupId = groupId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun createGroupBoard(
        groupId: Long,
        isChatRoom: Boolean,
        board: BoardManipulation
    ): Result<Long> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createGroupBoard(
                    path = serverName,
                    isChatRoom = isChatRoom,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    boardId = boardId,
                    requestBody = content
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getBoardArticles(
        boardId: Long,
        startWeight: Long?, // Optional
        fetch: Int
    ): Result<GetGroupBoardArticlesResponse> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getBoardArticles(
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    path = serverName,
                    boardId,
                    startWeight,
                    fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun deleteGroupArticle(articleId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteGroupArticle(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                ).checkResponseBody(jsonParser)
            }
        }
    }

    @Deprecated("推播層級已由社團改至看板，請使用getGroupBoardPushSetting")
    override suspend fun getGroupPushSetting(groupId: Long): Result<PushType> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val result = service.getGroupPushSetting(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    groupId = groupId
                ).checkResponseBody(jsonParser)
                PushType.values().find { it.value == result.pushType } ?: PushType.NONE
            }
        }
    }

    @Deprecated("推播層級已由社團改至看板，請使用setGroupBoardPushSetting")
    override suspend fun setGroupPushSetting(groupId: Long, pushType: PushType): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.setGroupPushSetting(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GroupPushSettingRequest(
                        groupId,
                        pushType.value
                    )
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getGroupBoardPushSetting(boardId: Long): Result<PushType> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                val result = service.getGroupBoardPushSetting(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    boardId = boardId
                ).checkResponseBody(jsonParser)

                PushType.values().find { it.value == result.pushType } ?: PushType.NONE
            }
        }
    }

    override suspend fun setGroupBoardPushSetting(boardId: Long, pushType: PushType): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.setGroupBoardPushSetting(
                    path = serverName,
                    boardId = boardId,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GroupBoardPushSettingRequestBody(
                        pushType.value
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
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
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(jsonParser).mapNotNull {
                    it.memberId
                }
            }
        }
    }

    override suspend fun changeCommentHideState(id: String, isHide: Boolean): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.changeCommentHideState(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = id,
                    body = HideCommentRequestBody(isHide)
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getSingleComment(commentId: String): Result<CommentResponseBodyV2> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSingleComment(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = commentId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getRecommendation(
        offset: Int,
        fetch: Int
    ): Result<GetRecommendationResponse> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getRecommendation(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getPinPromotedArticles(): Result<List<PromotedArticleResponseBody>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPinPromotedArticles(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getPromotedArticles(
        startWeight: Long,
        fetch: Int
    ): Result<GetPromotedArticlesResponse> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPromotedArticles(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    startWeight = startWeight,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun unsendArticle(articleId: Long): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.unsendArticle(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getAllChatRoom(): Result<List<GetAllChatRoomResponse>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getAllChatRoom(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getUncheckChatRoomCount(): Result<GetUncheckChatRoomCountResponse> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getUncheckChatRoomCount(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun resetUncheckChatRoomCount(): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.resetUncheckChatRoomCount(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun pinSpaceBoardArticle(articleId: String): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.pinSpaceBoardArticle(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun unpinSpaceBoardArticle(articleId: String): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.unpinSpaceBoardArticle(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    articleId = articleId
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun getSpaceBoardPinArticles(boardId: Long): Result<GetSpaceBoardPinArticlesResponseBody> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSpaceBoardPinArticles(
                    path = serverName,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    boardId = boardId
                ).checkResponseBody(jsonParser)
            }
        }
    }
}
