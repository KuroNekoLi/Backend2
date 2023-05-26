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
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember2
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
import com.cmoney.backend2.forumocean.service.api.relationship.getrelationshipwithme.RelationshipWithMe
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

    override suspend fun getReactionDetailV2(
        id: String,
        reactions: List<ReactionType>,
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<MemberEmojis> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getReactionDetailV2(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    emojiTypes = reactions.joinToString { it.value.toString() },
                    offset = offset,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
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

    override suspend fun deleteReaction(
        id: String,
        domain: String,
        url: String
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteReaction(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun createArticleInterest(
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.createArticleInterest(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
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

    override suspend fun block(
        memberId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.block(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unblock(
        memberId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.unblock(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberId = memberId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getBlockingList(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<Long>> = withContext(dispatcher.io()) {
        runCatching {
            service.getBlockingList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch

            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getBlockers(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<Long>> = withContext(dispatcher.io()) {
        runCatching {
            service.getBlockers(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getRelationshipWithMe(
        memberIdList: List<Long>,
        domain: String,
        url: String
    ): Result<List<RelationshipWithMe>> = withContext(dispatcher.io()) {
        runCatching {
            service.getRelationshipWithMe(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberIds = memberIdList.joinToString()
            ).checkResponseBody(jsonParser)
        }
    }

    @Deprecated("檢舉留言請使用createReportV2，檢舉主文仍暫時使用這個，待服務實作後遷移")
    override suspend fun createReport(
        articleId: Long,
        reasonType: Int,
        commentId: Long?,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.createReport(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                reasonType = reasonType,
                commentId = commentId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun createReportV2(
        id: String,
        reasonType: Int,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.createReportV2(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ReportRequestBody(reasonType)
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun deleteReport(
        articleId: Long,
        commentId: Long?,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.deleteReport(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commentId = commentId
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getMemberIds(
        channelIdList: List<Long>,
        domain: String,
        url: String
    ): Result<List<ChannelIdAndMemberId>> = withContext(dispatcher.io()) {
        runCatching {
            service.getMemberIds(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                channelIds = channelIdList.joinToString(",")
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getChannelIds(
        memberIdList: List<Long>,
        domain: String,
        url: String
    ): Result<List<ChannelIdAndMemberId>> = withContext(dispatcher.io()) {
        runCatching {
            service.getChannelIds(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberIds = memberIdList.joinToString(",")
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun searchMembers(
        keyword: String,
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<SearchMembersResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.searchMembers(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                keyword = keyword,
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun createVote(
        articleId: Long,
        optionIndex: Int,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.createVote(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                optionIndex = optionIndex
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getCurrentVote(
        articleId: Long,
        domain: String,
        url: String
    ): Result<List<VoteInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCurrentVote(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(jsonParser)
            }
        }

    override suspend fun getCommodityRank(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<GetCommodityRankResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommodityRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getUSCommodityRank(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<GetCommodityRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getUSCommodityRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getExpertMemberRank(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<GetExpertMemberRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getExpertMemberRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getSpecificExpertMemberRank(
        memberIds: String,
        domain: String,
        url: String
    ): Result<List<GetExpertMemberRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getSpecificExpertMemberRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberIds = memberIds
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getMemberFansRank(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<FansMemberRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getMemberFansRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getSpecificMemberFansRank(
        memberIds: String,
        domain: String,
        url: String
    ): Result<List<FansMemberRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getSpecificMemberFansRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberIds = memberIds
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getSolutionExpertRank(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<SolutionExpertRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getSolutionExpertRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getSpecificSolutionExpertRank(
        memberIds: String,
        domain: String,
        url: String
    ): Result<List<SolutionExpertRankResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getSpecificSolutionExpertRank(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                memberIds = memberIds
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getNotify(
        updateTime: Long,
        offsetCount: Int,
        domain: String,
        url: String
    ): Result<List<GetNotifyResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotify(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                updateTime = updateTime,
                offsetCount = offsetCount
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getNotifyCount(
        domain: String,
        url: String
    ): Result<GetNotifyCountResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun resetNotifyCount(domain: String, url: String): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                service.resetCount(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun setNotifyRead(
        notifyType: String,
        mergeKey: String,
        isNew: Boolean,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.setRead(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                notifyType = notifyType,
                mergeKey = mergeKey,
                isNew = isNew
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun exchangeColumnArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.exchangeColumnArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            )
            if (response.code() >= 400) {
                throw response.parseServerException(jsonParser)
            }
            Unit
        }
    }

    override suspend fun getRole(
        domain: String,
        url: String
    ): Result<Set<Role>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getRole(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)

            val roles = Role.values()
            return@runCatching response.mapNotNull { apiValue ->
                roles.firstOrNull { it.value == apiValue }
            }.toSet()
        }
    }

    override suspend fun getMembersByRole(
        roleId: Int,
        domain: String,
        url: String
    ): Result<List<Long>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getMembersByRoleId(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
            response.memberIds ?: listOf()
        }
    }

    override suspend fun getRoleByMemberId(
        memberId: Long,
        domain: String,
        url: String
    ): Result<Set<Role>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getRoleByMemberId(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
            val roles = Role.values()
            return@runCatching response.mapNotNull { apiValue ->
                roles.firstOrNull { it.value == apiValue }
            }.toSet()
        }
    }

    override suspend fun getExchangeCount(
        memberId: Long,
        domain: String,
        url: String
    ): Result<ExchangeCount> = withContext(dispatcher.io()) {
        runCatching {
            service.getExchangeCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun isMemberSubscribe(
        memberId: Long,
        domain: String,
        url: String
    ): Result<Boolean> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.isMemberSubscribe(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            )
            if (response.code() == 200) {
                response.body()?.string()?.trim() == "true"
            } else {
                throw ServerException(response.code(), "")
            }
        }
    }

    override suspend fun getColumnistVipGroup(
        columnistMemberId: Long,
        domain: String,
        url: String
    ): Result<GetColumnistVipGroupResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getColumnistVipGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getStockReportId(
        date: String,
        brokerId: String,
        stockId: String,
        domain: String,
        url: String
    ): Result<Int> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockReportId(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                date = date,
                brokerId = brokerId,
                stockId = stockId
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getGroupV2(
        groupId: Long,
        domain: String,
        url: String
    ): Result<Group> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getGroupV2(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getGroupByRoles(
        memberId: Long?,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>,
        domain: String,
        url: String
    ): Result<List<Group>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getGroupsByRole(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    roles = roles.joinToString(",") { it.value },
                    memberId = memberId
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun createGroup(
        group: GroupManipulation,
        domain: String,
        url: String
    ): Result<Long> = withContext(dispatcher.io()) {
        runCatching {
            service.createGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = group
            ).checkResponseBody(jsonParser).id?.toLong() ?: 0L
        }
    }

    override suspend fun updateGroup(
        groupId: Long,
        group: GroupManipulation,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.updateGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = group
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun dismissGroup(
        groupId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.deleteGroupV2(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun createGroupBoard(
        groupId: Long,
        isChatRoom: Boolean,
        board: BoardManipulation,
        domain: String,
        url: String
    ): Result<Long> = withContext(dispatcher.io()) {
        runCatching {
            service.createGroupBoard(
                url = url,
                isChatRoom = isChatRoom,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = board
            ).checkResponseBody(jsonParser).id?.toLong() ?: 0L
        }
    }

    override suspend fun updateGroupBoard(
        boardId: Long,
        board: BoardManipulation,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.updateGroupBoard(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = board
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getGroupBoards(
        groupId: Long,
        domain: String,
        url: String
    ): Result<List<Board>> = withContext(dispatcher.io()) {
        runCatching {
            service.getGroupBoards(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getGroupBoard(
        boardId: Long,
        domain: String,
        url: String
    ): Result<BoardSingle> = withContext(dispatcher.io()) {
        runCatching {
            service.getGroupBoard(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun deleteGroupBoard(
        boardId: Long,
        domain: String,
        url: String
    ): Result<Unit> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteGroupBoard(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }
    }

    override suspend fun hasNewGroupPending(
        groupId: Long,
        domain: String,
        url: String
    ): Result<Boolean> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.hasNewGroupPending(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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

    override suspend fun getGroupMemberRoles(
        groupId: Long,
        memberId: Long,
        domain: String,
        url: String
    ): Result<MemberRoles> = withContext(dispatcher.io()) {
        runCatching {
            service.getGroupMemberRoles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun updateGroupMemberRoles(
        groupId: Long,
        memberId: Long,
        roleIds: List<Int>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.updateGroupMemberRoles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                roles = roleIds
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getGroupMembers(
        groupId: Long,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>,
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<GroupMember2>> = withContext(dispatcher.io()) {
        runCatching {
            service.getGroupMembers(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                roles = roles.joinToString(",") { it.value },
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun leaveGroup(groupId: Long, domain: String, url: String): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                service.leaveGroup(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).handleNoContent(jsonParser)
            }
        }

    override suspend fun getGroupAdmins(
        groupId: Long,
        domain: String,
        url: String
    ): Result<Admins> = withContext(dispatcher.io()) {
        runCatching {
            service.getGroupAdmins(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun searchGroupMember(
        groupId: Long,
        keyword: String,
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<List<GroupMember>> = withContext(dispatcher.io()) {
        runCatching {
            service.searchGroupMember(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                keyword = keyword,
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun joinGroup(
        groupId: Long,
        joinGroupRequest: JoinGroupRequest,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.joinGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = joinGroupRequest
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getGroupPendingRequests(
        groupId: Long,
        timestamp: Long,
        fetch: Int,
        domain: String,
        url: String
    ): Result<PendingRequests> = withContext(dispatcher.io()) {
        runCatching {
            service.getGroupPendingRequests(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                timestamp = timestamp,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun searchGroupPendingRequests(
        groupId: Long,
        keyword: String,
        timestamp: Long,
        fetch: Int,
        domain: String,
        url: String
    ): Result<PendingRequests> = withContext(dispatcher.io()) {
        runCatching {
            service.searchGroupPendingRequests(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                keyword = keyword,
                timestamp = timestamp,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun approvalGroupRequest(
        groupId: Long,
        approval: List<Approval>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.approvalGroupRequest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = approval
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun kickGroupMember(
        groupId: Long,
        memberId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.kickGroupMember(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun createGroupArticle(
        boardId: Long,
        content: Content.Article.General,
        domain: String,
        url: String
    ): Result<CreateArticleResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.createGroupArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = content
            ).checkResponseBody(jsonParser)
        }

    }

    override suspend fun getBoardArticles(
        boardId: Long,
        startWeight: Long?,
        fetch: Int,
        domain: String,
        url: String
    ): Result<GetGroupBoardArticlesResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getBoardArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                startWeight = startWeight,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun unsendArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.unsendArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getAvailableBoardIds(
        domain: String,
        url: String
    ): Result<AvailableBoardIds> = withContext(dispatcher.io()) {
        runCatching {
            service.getAvailableBoardIds(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getGroupBoardPushSetting(
        boardId: Long,
        domain: String,
        url: String
    ): Result<PushType> = withContext(dispatcher.io()) {
        runCatching {
            val result = service.getGroupBoardPushSetting(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)

            PushType.values().find { it.value == result.pushType } ?: PushType.NONE
        }
    }

    override suspend fun setGroupBoardPushSetting(
        boardId: Long,
        pushType: PushType,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.setGroupBoardPushSetting(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GroupBoardPushSettingRequestBody(
                    pushType.value
                )
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getMemberRatingCounter(
        memberId: Long,
        domain: String,
        url: String
    ): Result<MemberRatingCounter> = withContext(dispatcher.io()) {
        runCatching {
            service.getMemberRatingCounter(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getRatingComment(
        creatorId: Long,
        memberId: Long,
        domain: String,
        url: String
    ): Result<RatingComment> = withContext(dispatcher.io()) {
        runCatching {
            service.getRatingComment(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getMemberRatingComments(
        memberId: Long,
        offset: Int,
        fetch: Int,
        sortType: SortType,
        domain: String,
        url: String
    ): Result<List<OthersRatingComment>> = withContext(dispatcher.io()) {
        runCatching {
            service.getMemberRatingComments(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                sortType = sortType.value,
                skipCount = offset,
                fetchCount = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun reviewUser(
        request: ReviewRequest,
        domain: String,
        url: String
    ): Result<String> = withContext(dispatcher.io()) {
        runCatching {
            service.reviewUser(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = request
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getColumnistAll(domain: String, url: String): Result<List<Long>> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getColumnistAll(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(jsonParser).mapNotNull {
                    it.memberId
                }
            }
        }
    }

    override suspend fun getArticleV2(
        articleId: Long,
        domain: String,
        url: String
    ): Result<ArticleResponseBodyV2> = withContext(dispatcher.io()) {
        runCatching {
            service.getArticleV2(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getCommentV2(
        articleId: String,
        startCommentIndex: Long?,
        fetch: Int?,
        domain: String,
        url: String
    ): Result<GetCommentsResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getCommentV2(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                startCommentIndex = startCommentIndex,
                fetch = fetch
            ).checkResponseBody(jsonParser)
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

    override suspend fun createCommentV2(
        id: String,
        text: String?,
        multiMedia: List<MediaType>?,
        domain: String,
        url: String
    ): Result<CreateCommentResponseBodyV2> = withContext(dispatcher.io()) {
        runCatching {
            service.createCommentV2(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = CreateCommentRequestBodyV2(
                    text = text,
                    multiMedia = multiMedia
                )
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun changeCommentHideState(
        id: String,
        isHide: Boolean,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.changeCommentHideState(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = HideCommentRequestBody(isHide)
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getSingleComment(
        commentId: String,
        domain: String,
        url: String
    ): Result<CommentResponseBodyV2> = withContext(dispatcher.io()) {
        runCatching {
            service.getSingleComment(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getRecommendation(
        offset: Int,
        fetch: Int,
        domain: String,
        url: String
    ): Result<GetRecommendationResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getRecommendation(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                offset = offset,
                fetch = fetch
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getPinPromotedArticles(
        domain: String,
        url: String
    ): Result<List<PromotedArticleResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getPinPromotedArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getPromotedArticles(
        startWeight: Long,
        fetch: Int,
        domain: String,
        url: String
    ): Result<GetPromotedArticlesResponse> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPromotedArticles(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    startWeight = startWeight,
                    fetch = fetch
                ).checkResponseBody(jsonParser)
            }
        }
    }

    override suspend fun getAllChatRoom(
        domain: String,
        url: String
    ): Result<List<GetAllChatRoomResponse>> = withContext(dispatcher.io()) {
        runCatching {
            service.getAllChatRoom(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun getUncheckChatRoomCount(
        domain: String,
        url: String
    ): Result<GetUncheckChatRoomCountResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getUncheckChatRoomCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkResponseBody(jsonParser)
        }
    }

    override suspend fun resetUncheckChatRoomCount(
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.resetUncheckChatRoomCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun pinSpaceBoardArticle(
        articleId: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.pinSpaceBoardArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun unpinSpaceBoardArticle(
        articleId: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.unpinSpaceBoardArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(jsonParser)
        }
    }

    override suspend fun getSpaceBoardPinArticles(
        boardId: Long,
        domain: String,
        url: String
    ): Result<GetSpaceBoardPinArticlesResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getSpaceBoardPinArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkResponseBody(jsonParser)
        }
    }
}
