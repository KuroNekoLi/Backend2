package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleRequestBody
import com.cmoney.backend2.forumocean.service.api.channel.getchannelsarticlebyweight.GetChannelsArticleByWeightRequestBody
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.chatroom.GetUncheckChatRoomCountResponse
import com.cmoney.backend2.forumocean.service.api.columnist.Columnist
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.hide.HideCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentRequestBody
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
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupPushSetting
import com.cmoney.backend2.forumocean.service.api.group.v2.InsertedId
import com.cmoney.backend2.forumocean.service.api.group.v2.JoinGroupRequest
import com.cmoney.backend2.forumocean.service.api.group.v2.MemberRoles
import com.cmoney.backend2.forumocean.service.api.group.v2.PendingRequests
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
import com.cmoney.backend2.forumocean.service.api.role.GetMembersByRoleResponse
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat.GetAllChatRoomResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat.GetGroupBoardArticlesResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.group.GetGroupAllLatestArticlesResponseBody
import com.cmoney.backend2.forumocean.service.api.schemas.v2.RecommendedClubsResponse
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
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ForumOceanService {

    @RecordApi
    @GET
    suspend fun getBanState(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetBanStateResponseBody>

    /**
     * 發專欄文章
     */
    @RecordApi
    @POST
    suspend fun createPersonalArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.PersonalArticle.Columnist
    ): Response<CreatePersonalArticleResponseBody>

    /**
     * 發筆記文
     */
    @RecordApi
    @POST
    suspend fun createPersonalArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.PersonalArticle.Note
    ): Response<CreatePersonalArticleResponseBody>

    /**
     * 發一般文
     *
     */
    @RecordApi
    @POST
    suspend fun createArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.General
    ): Response<CreateArticleResponseBody>

    /**
     * 發社團文
     *
     */
    @RecordApi
    @POST
    suspend fun createArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.Group
    ): Response<CreateArticleResponseBody>

    /**
     * 發轉推文
     *
     */
    @RecordApi
    @POST
    suspend fun createArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.Shared
    ): Response<CreateArticleResponseBody>

    /**
     * 發問答文章
     */
    @RecordApi
    @POST
    suspend fun createQuestion(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Question
    ): Response<CreateQuestionResponseBody>

    /**
     * 發專欄文章
     */
    @RecordApi
    @POST
    suspend fun createArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.Column
    ): Response<CreateArticleResponseBody>

    /**
     * 修改文章資訊
     */
    @RecordApi
    @PUT
    suspend fun updateArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateArticleRequestBody
    ): Response<Void>

    /**
     * 刪除文章
     */
    @RecordApi
    @Deprecated("待服務實作完成，使用deleteArticleV2")
    @DELETE
    suspend fun deleteArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 刪除文章V2
     */
    @RecordApi
    @Headers("X-Version: 2.0")
    @DELETE
    suspend fun deleteArticleV2(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getMemberStatistics(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberIds") memberIds: String
    ): Response<List<GetMemberStatisticsResponseBody>>

    @RecordApi
    @POST
    suspend fun getChannelsArticleByWeight(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body channelNameList: GetChannelsArticleByWeightRequestBody,
        @Query("startScore") startScore: Long?,
        @Query("count") count: Int
    ): Response<List<ArticleResponseBody.UnknownArticleResponseBody>>

    @RecordApi
    @POST
    suspend fun createCollection(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun deleteCollection(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getGroupManagerComments(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<CommentResponseBody>>

    @RecordApi
    @PUT
    suspend fun updateComment(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateCommentRequestBody
    ): Response<Void>

    @RecordApi
    @Headers("X-Version: 2.0")
    @DELETE
    suspend fun deleteCommentV2(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @Headers("X-Version: 2.0")
    @GET
    suspend fun getReactionDetailV2(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("emojiTypes") emojiTypes: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<MemberEmojis>

    @RecordApi
    @Headers("X-Version: 2.0")
    @PUT
    suspend fun createReaction(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun createArticleDonate(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("donateValue") donateValue: Int
    ): Response<Void>

    @RecordApi
    @Headers("X-Version: 2.0")
    @DELETE
    suspend fun deleteReaction(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun createArticleInterest(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getArticleDonate(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<DonateInfo>>

    @RecordApi
    @GET
    suspend fun getGroup(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GroupResponseBody>

    @RecordApi
    @GET
    suspend fun getGroupsWithPosition(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long,
        @Query("position") position: Int,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int,
        @Query("includeAppGroup") includeAppGroup: Boolean
    ): Response<List<GroupResponseBody>>

    @RecordApi
    @GET
    suspend fun getMemberJoinAnyGroups(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<GetMemberJoinAnyGroupsResponseBody>

    @RecordApi
    @POST
    suspend fun createGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("groupName") groupName: String
    ): Response<CreateGroupResponseBody>

    @RecordApi
    @PUT
    suspend fun updateGroupV1(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: UpdateGroupRequestBody
    ): Response<Void>

    @RecordApi
    @PUT
    suspend fun transferGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun deleteGroup(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun join(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("reason") reason: String?
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getMembers(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int,
        @Query("position") position: Int
    ): Response<List<com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember>>

    @RecordApi
    @GET
    suspend fun getApprovals(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GroupPendingApproval>>

    @RecordApi
    @POST
    suspend fun approval(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long,
        @Query("isAgree") isAgree: Boolean
    ): Response<Void>

    @RecordApi
    @PUT
    suspend fun changeGroupMemberPosition(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long,
        @Query("position") position: Int
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun kick(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun leave(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun pinArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun unpinArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getPushDefaultSetting(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<NotifyPushSetting>>

    @RecordApi
    @GET
    suspend fun getUserNotifySetting(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<NotifyPushSetting>>

    @RecordApi
    @POST
    suspend fun setNotifySetting(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("type") notifyType: String,
        @Query("subType") subType: String = "",
        @Query("enable") enable: Boolean
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getOfficials(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET
    suspend fun getOfficialsByIds(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("officialIds") officialIds: String
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET
    suspend fun getOfficialsByKeyword(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET
    suspend fun getGroupsByKeyword(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GroupResponseBody>>

    @RecordApi
    @GET
    suspend fun getOfficialSubscribedCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetOfficialSubscribedCountResponseBody>

    @RecordApi
    @GET
    suspend fun getSubscribedCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<GetSubscribedCountResponseBody>

    @RecordApi
    @GET
    suspend fun getSubscribed(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Int>>

    @RecordApi
    @POST
    suspend fun subscribe(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun unsubscribe(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Path("officialId") officialId: Long
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun unsubscribeAll(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getFollowingList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @GET
    suspend fun getFollowers(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @POST
    suspend fun follow(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun unfollow(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun block(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun unblock(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getBlockingList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @GET
    suspend fun getBlockers(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @GET
    suspend fun getRelationshipWithMe(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberIds") memberIds: String
    ): Response<List<RelationshipWithMe>>

    @RecordApi
    @Deprecated("檢舉留言請使用createReportV2，檢舉主文仍暫時使用這道，待服務實作後遷移")
    @POST
    suspend fun createReport(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("reasonType") reasonType: Int,
        @Query("commentId") commentId: Long?
    ): Response<Void>

    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun createReportV2(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: ReportRequestBody
    ): Response<Void>

    @RecordApi
    @DELETE
    suspend fun deleteReport(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("commentId") commentId: Long?
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getMemberIds(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("channelIds") channelIds: String
    ): Response<List<ChannelIdAndMemberId>>

    @RecordApi
    @GET
    suspend fun getChannelIds(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberIds") memberIds: String
    ): Response<List<ChannelIdAndMemberId>>

    @RecordApi
    @GET
    suspend fun searchMembers(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<SearchMembersResponseBody>>

    @RecordApi
    @POST
    suspend fun createVote(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("optionIndex") optionIndex: Int
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getCurrentVote(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<VoteInfo>>

    /**
     * 取得個股排行(不含美股)
     */
    @RecordApi
    @GET
    suspend fun getCommodityRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GetCommodityRankResponseBody>>

    /**
     * 取得美股排行
     */
    @RecordApi
    @GET
    suspend fun getUSCommodityRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GetCommodityRankResponseBody>>

    /**
     * 取得達人排行
     */
    @RecordApi
    @GET
    suspend fun getExpertMemberRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GetExpertMemberRankResponseBody>>

    /**
     * 取得指定達人排行
     */
    @RecordApi
    @GET
    suspend fun getSpecificExpertMemberRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberIds") memberIds: String
    ): Response<List<GetExpertMemberRankResponseBody>>


    /**
     * 取得粉絲成長達人排行
     */
    @RecordApi
    @GET
    suspend fun getMemberFansRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<FansMemberRankResponseBody>>

    /**
     * 取得指定粉絲成長達人排行
     */
    @RecordApi
    @GET
    suspend fun getSpecificMemberFansRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberIds") memberIds: String
    ): Response<List<FansMemberRankResponseBody>>


    /**
     * 取得解題達人排行
     */
    @RecordApi
    @GET
    suspend fun getSolutionExpertRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<SolutionExpertRankResponseBody>>

    /**
     * 取得指定解題達人排行
     */
    @RecordApi
    @GET
    suspend fun getSpecificSolutionExpertRank(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberIds") memberIds: String
    ): Response<List<SolutionExpertRankResponseBody>>

    @RecordApi
    @GET
    suspend fun getNotify(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("updateTime") updateTime: Long,
        @Query("offsetCount") offsetCount: Int
    ): Response<List<GetNotifyResponseBody>>

    @RecordApi
    @GET
    suspend fun getCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetNotifyCountResponseBody>

    @RecordApi
    @POST
    suspend fun resetCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun setRead(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("type") notifyType: String,
        @Query("mergeKey") mergeKey: String,
        @Query("isNew") isNew: Boolean
    ): Response<Void>

    @RecordApi
    @POST
    suspend fun exchangeColumnArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET
    suspend fun getRole(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<Int>>

    @RecordApi
    @GET
    suspend fun getMembersByRoleId(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetMembersByRoleResponse>

    @RecordApi
    @GET
    suspend fun getRoleByMemberId(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<Int>>

    @RecordApi
    @GET
    suspend fun getExchangeCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ExchangeCount>

    @RecordApi
    @GET
    suspend fun isMemberSubscribe(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    @RecordApi
    @GET
    suspend fun getColumnistVipGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
    ): Response<GetColumnistVipGroupResponse>

    /**
     * 取得指定研究報告文章ID
     */
    @RecordApi
    @GET
    suspend fun getStockReportId(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("date") date: String,
        @Query("brokerId") brokerId: String,
        @Query("stockId") stockId: String
    ): Response<Int>

    /**
     * 取得指定社團資訊
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupV2(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Group>

    /**
     * 依角色取得會員所有社團(MemberId不帶等於取自己)
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupsByRole(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long?, // Optional
        @Query("roleTypes", encoded = true) roles: String
    ): Response<List<Group>>

    /**
     * 創建社團
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun createGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GroupManipulation
    ): Response<InsertedId>

    /**
     * 更新社團資訊
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun updateGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GroupManipulation
    ): Response<Void>

    /**
     * 增加社團的板(鎖權限:社長)
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun createGroupBoard(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("isChatRoom") isChatRoom: Boolean,
        @Body body: BoardManipulation
    ): Response<InsertedId>

    /**
     * 解散社團(鎖權限：社長)
     */
    @RecordApi
    @DELETE
    @Headers("X-Version: 2.0")
    suspend fun deleteGroupV2(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 修改看板
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun updateGroupBoard(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: BoardManipulation
    ): Response<Void>

    /**
     * 取得社團所有看板
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupBoards(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<Board>>

    /**
     * 取得特定看板資訊
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupBoard(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<BoardSingle>

    /**
     * 刪除社團看板(鎖權限：幹部以上)
     */
    @RecordApi
    @DELETE
    @Headers("X-Version: 2.0")
    suspend fun deleteGroupBoard(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 取得社團是否有未察看的待審用戶
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun hasNewGroupPending(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    /**
     * 取得該社員在社團的所有角色
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupMemberRoles(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<MemberRoles>

    /**
     * 設定社團身份(鎖權限：幹部以上)
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun updateGroupMemberRoles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body roles: List<Int>
    ): Response<Void>

    /**
     * 取得社團成員列表
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupMembers(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("roleTypes", encoded = true) roles: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GroupMember2>>

    /**
     * 離開社團
     */
    @RecordApi
    @DELETE
    @Headers("X-Version: 2.0")
    suspend fun leaveGroup(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 取得社長及幹部清單
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupAdmins(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Admins>

    /**
     * 搜尋社員
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun searchGroupMember(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember>>

    /**
     * 申請加入社團
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun joinGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: JoinGroupRequest
    ): Response<Void>

    /**
     * 取得審核成員列表(鎖權限：幹部以上)
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupPendingRequests(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("timestamp") timestamp: Long,
        @Query("fetch") fetch: Int = 20
    ): Response<PendingRequests>

    /**
     * 搜尋審核中的社員(鎖權限：幹部以上)
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun searchGroupPendingRequests(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String,
        @Query("timestamp") timestamp: Long?,
        @Query("fetch") fetch: Int = 20
    ): Response<PendingRequests>

    /**
     * 審核成員加入(鎖權限：幹部以上)
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun approvalGroupRequest(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: List<Approval>
    ): Response<Void>

    /**
     * 踢出成員(鎖權限：幹部以上)
     */
    @RecordApi
    @DELETE
    @Headers("X-Version: 2.0")
    suspend fun kickGroupMember(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 對社團看板發文
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun createGroupArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.General
    ): Response<CreateArticleResponseBody>

    /**
     * 刪除社團文章
     */
    @RecordApi
    @DELETE
    @Headers("X-Version: 2.0")
    suspend fun deleteGroupArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
    ): Response<Void>

    /**
     * 取得指定看板文章
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getBoardArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("startWeight") startWeight: Long?, // Optional.
        @Query("fetch") fetch: Int
    ): Response<GetGroupBoardArticlesResponse>

    /**
     * 聊天室: 收回自己的訊息
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun unsendArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 取得用戶可以進入的所有看板 id
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getAvailableBoardIds(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("excludeChatroom") excludeChatroom: Boolean,
    ): Response<AvailableBoardIds>

    /**
     * 取得會員在社團看板的推播設定
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getGroupBoardPushSetting(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GroupPushSetting>

    /**
     * 設定會員在社團看板的推播
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun setGroupBoardPushSetting(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GroupBoardPushSettingRequestBody
    ): Response<Void>

    /**
     * 取得會員的被評價資訊統計
     */
    @RecordApi
    @GET
    suspend fun getMemberRatingCounter(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<MemberRatingCounter>

    /**
     * 取得指定評價
     */
    @RecordApi
    @GET
    suspend fun getRatingComment(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<RatingComment>

    /**
     * 取得指定會員的被評價清單
     */
    @RecordApi
    @GET
    suspend fun getMemberRatingComments(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("sortType") sortType: Int,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<List<OthersRatingComment>>

    /**
     * 滿分為5, 評論字數不可多於200
     */
    @RecordApi
    @PUT
    suspend fun reviewUser(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: ReviewRequest
    ): Response<String>

    @RecordApi
    @GET
    suspend fun getColumnistAll(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<Columnist>>


    /**
     * 取得文章資訊v2
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getArticleV2(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ArticleResponseBodyV2>

    /**
     * 取得留言V2(可訪客)
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getCommentV2(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("startCommentIndex") startCommentIndex: Long?,
        @Query("fetch") fetch: Int?
    ): Response<GetCommentsResponseBody>

    /**
     * 取得指定 index 的留言
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getCommentsByIndex(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("articleId") articleId: String,
        @Query("commentIndex") commentIndex: String
    ): Response<List<CommentResponseBodyV2>>

    /**
     * 發留言v2
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun createCommentV2(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CreateCommentRequestBodyV2
    ): Response<CreateCommentResponseBodyV2>

    /**
     * 隱藏/取消隱藏留言
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun changeCommentHideState(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: HideCommentRequestBody
    ): Response<Void>

    /**
     * 取得單一留言
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getSingleComment(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<CommentResponseBodyV2>

    /**
     * 取得個股最相關文章
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getMostRelevantCommodityArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Long?,
        @Query("fetch") fetch: Int
    ): Response<List<ArticleResponseBody.UnknownArticleResponseBody>>

    /**
     * 取得大盤最相關文章
     *
     * @param url 要求網址
     * @param authorization 授權Token
     * @param offset 起始權重 (不帶從頭取)
     * @param fetch 取得數量
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getMostRelevantMarketArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Long?,
        @Query("fetch") fetch: Int
    ): Response<List<ArticleResponseBody.UnknownArticleResponseBody>>

    /**
     * 取得個人化推薦文章
     */
    @RecordApi
    @GET
    suspend fun getRecommendation(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<GetRecommendationResponse>

    /**
     * 取得置頂精選文章清單
     */
    @RecordApi
    @GET
    suspend fun getPinPromotedArticles(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<PromotedArticleResponseBody>>

    /**
     * 取得精選文章清單
     */
    @RecordApi
    @GET
    suspend fun getPromotedArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("startWeight") startWeight: Long,
        @Query("fetch") fetch: Int
    ): Response<GetPromotedArticlesResponse>

    /**
     * 取得聊天室列表
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getAllChatRoom(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<GetAllChatRoomResponse>>

    /**
     * 取得使用者未檢查的聊天室看板數
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getUncheckChatRoomCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetUncheckChatRoomCountResponse>

    /**
     * 重設使用者未檢查的聊天室看板數
     */
    @RecordApi
    @PUT
    @Headers("X-Version: 2.0")
    suspend fun resetUncheckChatRoomCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 釘選社團看板文章
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun pinSpaceBoardArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 取消釘選社團看板文章
     */
    @RecordApi
    @POST
    @Headers("X-Version: 2.0")
    suspend fun unpinSpaceBoardArticle(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 取得社團看板置頂文章列表
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getSpaceBoardPinArticles(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<GetSpaceBoardPinArticlesResponseBody>

    /**
     * 取得用戶不分社團所有非聊天室看板文章，排序為新到舊
     * 僅能取得社團2.0的文章
     * @param authorization 登入憑證
     * @param startWeight 起始權重，不帶則預設值為long的最大值
     * @param articlesNumber 取文篇數，不帶的話預設為10
     *
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getJoinedClubArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("startWeight") startWeight: Long? = null,
        @Query("fetch") articlesNumber: Int? = null,
    ): Response<GetGroupAllLatestArticlesResponseBody>

    /**
     * 推薦使用者未加入的社團
     *
     * @param authorization 登入憑證
     */
    @RecordApi
    @GET
    @Headers("X-Version: 2.0")
    suspend fun getRecommendedClub(
        @Url url: String,
        @Header("Authorization") authorization: String
    ):Response<RecommendedClubsResponse>
}

