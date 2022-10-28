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
import com.cmoney.backend2.forumocean.service.api.columnist.Columnist
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.hide.HideCommentResponseBody
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
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupManipulation
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember2
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupPushSetting
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupPushSettingRequest
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
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.recommendations.GetRecommendationResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.GetCommentsResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.MemberEmojis
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
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

interface ForumOceanService {

    @RecordApi
    @GET("{path}/api/Article/GetBanState")
    suspend fun getBanState(
        @Path("path") path: String,
        @Header("Authorization") authorization: String
    ): Response<GetBanStateResponseBody>

    /**
     * 發專欄文章
     */
    @RecordApi
    @POST("{path}/api/Article/{articleType}")
    suspend fun createPersonalArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleType") articleType: String,
        @Body requestBody: Content.PersonalArticle.Columnist
    ): Response<CreatePersonalArticleResponseBody>

    /**
     * 發筆記文
     */
    @RecordApi
    @POST("{path}/api/Article/{articleType}")
    suspend fun createPersonalArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleType") articleType: String,
        @Body requestBody: Content.PersonalArticle.Note
    ): Response<CreatePersonalArticleResponseBody>

    /**
     * 發一般文
     *
     */
    @RecordApi
    @POST("{path}/api/Article/Create")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body requestBody: Content.Article.General
    ): Response<CreateArticleResponseBody>

    /**
     * 發社團文
     *
     */
    @RecordApi
    @POST("{path}/api/Article/Create")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body requestBody: Content.Article.Group
    ): Response<CreateArticleResponseBody>

    /**
     * 發轉推文
     *
     */
    @RecordApi
    @POST("{path}/api/Article/Create")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body requestBody: Content.Article.Shared
    ): Response<CreateArticleResponseBody>

    /**
     * 發問答文章
     */
    @RecordApi
    @POST("{path}/api/Article/CreateQuestion")
    suspend fun createQuestion(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body requestBody: Content.Question
    ): Response<CreateQuestionResponseBody>

    /**
     * 發專欄文章
     */
    @RecordApi
    @POST("{path}/api/article/columnist")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body requestBody: Content.Article.Column
    ): Response<CreateArticleResponseBody>

    /**
     * 取得文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.GeneralArticleResponseBody>

    /**
     * 取得問答文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getQuestionArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.QuestionArticleResponseBody>

    /**
     * 取得新聞文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getNewsArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.NewsArticleResponseBody>

    /**
     * 取得訊號文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getSignalArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.SignalArticleResponseBody>

    /**
     * 取得社團文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getGroupArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.GroupArticleResponseBody>

    /**
     * 取得轉推文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getSharedArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.SharedArticleResponseBody>

    /**
     * 取得個人文章資訊(專欄文章/筆記)
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getPersonalArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.PersonalArticleResponseBody>

    /**
     * 取得未知文章資訊
     *
     */
    @RecordApi
    @Deprecated("請使用getArticleV2")
    @GET("{path}/api/Article/Get/{articleId}")
    suspend fun getUnknownArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.UnknownArticleResponseBody>

    /**
     * 修改文章資訊
     */
    @RecordApi
    @PUT("{path}/api/Article/Update/{articleId}")
    suspend fun updateArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Body body: UpdateArticleRequestBody
    ): Response<Void>

    /**
     * 刪除文章
     */
    @RecordApi
    @Deprecated("待服務實作完成，使用deleteArticleV2")
    @DELETE("{path}/api/Article/Delete/{articleId}")
    suspend fun deleteArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    /**
     * 刪除文章V2
     */
    @RecordApi
    @Headers("X-Version: 2.0")
    @DELETE("{path}/api/Article/{articleId}")
    suspend fun deleteArticleV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Member/Info")
    suspend fun getMemberStatistics(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberIds") memberIds: String
    ): Response<List<GetMemberStatisticsResponseBody>>

    @RecordApi
    @POST("{path}/api/Channel/GetChannelsArticleByWeight")
    suspend fun getChannelsArticleByWeight(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body channelNameList: GetChannelsArticleByWeightRequestBody,
        @Query("startScore") startScore: Long,
        @Query("count") count: Int
    ): Response<List<ArticleResponseBody.UnknownArticleResponseBody>>

    /**
     * First page of api getChannelsArticleByWeight
     * @see getChannelsArticleByWeight
     */
    @RecordApi
    @POST("{path}/api/Channel/GetChannelsArticleByWeight")
    suspend fun getChannelsArticleByWeight(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body channelNameList: GetChannelsArticleByWeightRequestBody,
        @Query("count") count: Int
    ): Response<List<ArticleResponseBody.UnknownArticleResponseBody>>

    @RecordApi
    @POST("{path}/api/Collection/Create/{articleId}")
    suspend fun createCollection(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/Collection/Delete/{articleId}")
    suspend fun deleteCollection(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @Deprecated("請使用createCommentV2")
    @POST("{path}/api/Comment/Create/{articleId}")
    suspend fun createComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Body body: CreateCommentRequestBody
    ): Response<CreateCommentResponseBody>

    @RecordApi
    @Deprecated("請使用createCommentV2")
    @POST("{path}/api/GroupArticle/{articleId}/Comment")
    @Headers("X-Version: 2.0")
    suspend fun createGroupArticleComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Body body: CreateCommentRequestBody
    ): Response<CreateCommentResponseBody>

    @RecordApi
    @Deprecated("請使用getCommentV2")
    @GET("{path}/api/Comment/Get/{articleId}")
    suspend fun getComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("commentId") commentId: Long?,
        @Query("offsetCount") offsetCount: Int?
    ): Response<List<CommentResponseBody>>

    @RecordApi
    @Deprecated("請使用getCommentsByIndex")
    @GET("{path}/api/Comment/GetWithIds/{articleId}")
    suspend fun getCommentWithId(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("commentIds") commentIds: String
    ): Response<List<CommentResponseBody>>

    @RecordApi
    @GET("{path}/api/Comment/GetGroupManagerComments/{articleId}")
    suspend fun getGroupManagerComments(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<List<CommentResponseBody>>

    @RecordApi
    @PUT("{path}/api/Comment/Update/{articleId}/{commentId}")
    suspend fun updateComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Path("commentId") commentId: Long,
        @Body body: UpdateCommentRequestBody
    ): Response<Void>

    @RecordApi
    @Deprecated("請使用deleteCommentV2")
    @DELETE("{path}/api/Comment/Delete/{articleId}/{commentIndex}")
    suspend fun deleteComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Long
    ): Response<Void>

    @RecordApi
    @Headers("X-Version: 2.0")
    @DELETE("{path}/api/Article/{articleId}")
    suspend fun deleteCommentV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/CommentInteractive/Reaction/{articleId}/{commentIndex}")
    suspend fun reactComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Long,
        @Query("reactionType") reactionType: Int
    ): Response<Void>

    @RecordApi
    @Deprecated("請使用getReactionDetailV2")
    @GET("{path}/api/CommentInteractive/GetReactionDetail/{articleId}/{commentIndex}")
    suspend fun getReactionDetail(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Long,
        @Query("reactions") reactions: String,
        @Query("skipCount") skipCount: Int,
        @Query("takeCount") takeCount: Int
    ): Response<List<ReactionInfo>>

    @RecordApi
    @Headers("X-Version: 2.0")
    @GET("{path}/api/Article/{articleId}/Emoji/Detail")
    suspend fun getReactionDetailV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String,
        @Query("emojiTypes") emojiTypes: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<MemberEmojis>

    @RecordApi
    @Deprecated("請使用deleteReaction")
    @DELETE("{path}/api/CommentInteractive/RemoveReaction/{articleId}/{commentIndex}")
    suspend fun removeCommentReaction(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Long
    ): Response<Void>

    @RecordApi
    @Deprecated("請使用createReaction")
    @POST("{path}/api/Interactive/Reaction/{articleId}")
    suspend fun createArticleReaction(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("reactionType") reactionType: Int
    ): Response<Void>

    @RecordApi
    @Headers("X-Version: 2.0")
    @PUT("{path}/api/Article/{articleId}/Emoji/{emojiType}")
    suspend fun createReaction(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String,
        @Path("emojiType") emojiType: Int
    ): Response<Void>

    @RecordApi
    @Deprecated("請使用getReactionDetailV2")
    @GET("{path}/api/Interactive/GetReactionDetail/{articleId}")
    suspend fun getArticleReactionDetail(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("reactions") reactions: String,
        @Query("skipCount") skipCount: Int,
        @Query("takeCount") count: Int
    ): Response<List<ReactionInfo>>

    @RecordApi
    @POST("{path}/api/Interactive/Donate/{articleId}")
    suspend fun createArticleDonate(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("donateValue") donateValue: Int
    ): Response<Void>

    @RecordApi
    @Deprecated("請使用deleteReaction")
    @DELETE("{path}/api/Interactive/RemoveReaction/{articleId}")
    suspend fun deleteArticleReaction(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @Headers("X-Version: 2.0")
    @DELETE("{path}/api/Article/{articleId}/Emoji")
    suspend fun deleteReaction(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/Interactive/Interest/{articleId}")
    suspend fun createArticleInterest(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Interactive/GetDonate/{articleId}")
    suspend fun getArticleDonate(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<DonateInfo>>

    @RecordApi
    @GET("{path}/api/Group/GetGroup/{groupId}")
    suspend fun getGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<GroupResponseBody>

    @RecordApi
    @GET("{path}/api/Group/GetGroupsWithPosition")
    suspend fun getGroupsWithPosition(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long,
        @Query("position") position: Int,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int,
        @Query("includeAppGroup") includeAppGroup: Boolean
    ): Response<List<GroupResponseBody>>

    @RecordApi
    @GET("{path}/api/Group/GetMemberJoinAnyGroups")
    suspend fun getMemberJoinAnyGroups(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long
    ): Response<GetMemberJoinAnyGroupsResponseBody>

    @RecordApi
    @POST("{path}/api/Group/Create")
    suspend fun createGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("groupName") groupName: String
    ): Response<CreateGroupResponseBody>

    @RecordApi
    @PUT("{path}/api/Group/Update/{groupId}")
    suspend fun updateGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Body body: UpdateGroupRequestBody
    ): Response<Void>

    @RecordApi
    @PUT("{path}/api/Group/TransferOwner/{groupId}")
    suspend fun transferGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/Group/Delete/{groupId}")
    suspend fun deleteGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/GroupMember/Join/{groupId}")
    suspend fun join(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("reason") reason: String?
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/GroupMember/GetMembers/{groupId}")
    suspend fun getMembers(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int,
        @Query("position") position: Int
    ): Response<List<com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember>>

    @RecordApi
    @GET("{path}/api/GroupMember/GetApprovals/{groupId}")
    suspend fun getApprovals(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GroupPendingApproval>>

    @RecordApi
    @POST("{path}/api/GroupMember/Approval/{groupId}")
    suspend fun approval(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long,
        @Query("isAgree") isAgree: Boolean
    ): Response<Void>

    @RecordApi
    @PUT("{path}/api/GroupMember/ChangeGroupMemberPosition/{groupId}")
    suspend fun changeGroupMemberPosition(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long,
        @Query("position") position: Int
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/GroupMember/Kick/{groupId}")
    suspend fun kick(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/GroupMember/Leave/{groupId}")
    suspend fun leave(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/GroupArticle/PinArticle/{articleId}")
    suspend fun pinArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/GroupArticle/UnpinArticle/{articleId}")
    suspend fun unpinArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/NotifySetting/GetPushDefaultSetting")
    suspend fun getPushDefaultSetting(
        @Path("path") path: String,
        @Header("Authorization") authorization: String
    ): Response<List<NotifyPushSetting>>

    @RecordApi
    @GET("{path}/api/NotifySetting/Get")
    suspend fun getUserNotifySetting(
        @Path("path") path: String,
        @Header("Authorization") authorization: String
    ): Response<List<NotifyPushSetting>>

    @RecordApi
    @POST("{path}/api/NotifySetting/Set")
    suspend fun setNotifySetting(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("type") notifyType: String,
        @Query("subType") subType: String = "",
        @Query("enable") enable: Boolean
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Official/GetOfficials")
    suspend fun getOfficials(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET("{path}/api/Official/GetOfficialsByIds")
    suspend fun getOfficialsByIds(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("officialIds") officialIds: String
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET("{path}/api/Official/GetOfficialsByKeyword")
    suspend fun getOfficialsByKeyword(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET("{path}/api/Group/GetGroupsByKeyword")
    suspend fun getGroupsByKeyword(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GroupResponseBody>>

    @RecordApi
    @GET("{path}/api/OfficialSubscriber/GetOfficialSubscribedCount/{officialId}")
    suspend fun getOfficialSubscribedCount(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("officialId") officialId: Long
    ): Response<GetOfficialSubscribedCountResponseBody>

    @RecordApi
    @GET("{path}/api/OfficialSubscriber/GetSubscribedCount")
    suspend fun getSubscribedCount(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long
    ): Response<GetSubscribedCountResponseBody>

    @RecordApi
    @GET("{path}/api/OfficialSubscriber/GetSubscribeds")
    suspend fun getSubscribed(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Int>>

    @RecordApi
    @POST("{path}/api/OfficialSubscriber/Subscribe/{officialId}")
    suspend fun subscribe(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("officialId") officialId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/OfficialSubscriber/Unsubscribe/{officialId}")
    suspend fun unsubscribe(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("officialId") officialId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/OfficialSubscriber/UnsubscribeAll")
    suspend fun unsubscribeAll(
        @Path("path") path: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Relationship/GetFollowingList/{memberId}")
    suspend fun getFollowingList(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("memberId") memberId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @GET("{path}/api/Relationship/GetFollowers/{memberId}")
    suspend fun getFollowers(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("memberId") memberId: Long,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @POST("{path}/api/Relationship/Follow")
    suspend fun follow(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/Relationship/Unfollow")
    suspend fun unfollow(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/Relationship/Block")
    suspend fun block(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/Relationship/Unblock")
    suspend fun unblock(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Relationship/GetBlockingList")
    suspend fun getBlockingList(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @GET("{path}/api/Relationship/GetBlockers")
    suspend fun getBlockers(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<Long>>

    @RecordApi
    @GET("{path}/api/Relationship/GetRelationshipWithMe")
    suspend fun getRelationshipWithMe(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberIds") memberIds: String
    ): Response<List<RelationshipWithMe>>

    @RecordApi
    @Deprecated("檢舉留言請使用createReportV2，檢舉主文仍暫時使用這道，待服務實作後遷移")
    @POST("{path}/api/Report/Create/{articleId}")
    suspend fun createReport(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("reasonType") reasonType: Int,
        @Query("commentId") commentId: Long?
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/Article/{articleId}/Report")
    suspend fun createReportV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String,
        @Body body: ReportRequestBody
    ): Response<Void>

    @RecordApi
    @DELETE("{path}/api/Report/Delete/{articleId}")
    suspend fun deleteReport(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("commentId") commentId: Long?
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Support/GetMemberIds")
    suspend fun getMemberIds(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("channelIds") channelIds: String
    ): Response<List<ChannelIdAndMemberId>>

    @RecordApi
    @GET("{path}/api/Support/GetChannelIds")
    suspend fun getChannelIds(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberIds") memberIds: String
    ): Response<List<ChannelIdAndMemberId>>

    @RecordApi
    @GET("{path}/api/Support/SearchMember")
    suspend fun searchMembers(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<SearchMembersResponseBody>>

    @RecordApi
    @POST("{path}/api/Vote/Create/{articleId}")
    suspend fun createVote(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Query("optionIndex") optionIndex: Int
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Vote/GetCurrentVotes/{articleId}")
    suspend fun getCurrentVote(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<List<VoteInfo>>

    /**
     * 取得個股排行(不含美股)
     */
    @RecordApi
    @GET("{path}/api/Rank/Commodity")
    suspend fun getCommodityRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GetCommodityRankResponseBody>>

    /**
     * 取得美股排行
     */
    @RecordApi
    @GET("{path}/api/Rank/USCommodity")
    suspend fun getUSCommodityRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GetCommodityRankResponseBody>>

    /**
     * 取得達人排行
     */
    @RecordApi
    @GET("{path}/api/Rank/ExpertMember")
    suspend fun getExpertMemberRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GetExpertMemberRankResponseBody>>

    /**
     * 取得指定達人排行
     */
    @RecordApi
    @GET("{path}/api/Rank/SpecificExpertMemberRanks")
    suspend fun getSpecificExpertMemberRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberIds") memberIds: String
    ): Response<List<GetExpertMemberRankResponseBody>>


    /**
     * 取得粉絲成長達人排行
     */
    @RecordApi
    @GET("{path}/api/Rank/MemberFansRank")
    suspend fun getMemberFansRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<FansMemberRankResponseBody>>

    /**
     * 取得指定粉絲成長達人排行
     */
    @RecordApi
    @GET("{path}/api/Rank/SpecificMemberFansRank")
    suspend fun getSpecificMemberFansRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberIds") memberIds: String
    ): Response<List<FansMemberRankResponseBody>>


    /**
     * 取得解題達人排行
     */
    @RecordApi
    @GET("{path}/api/Rank/SolutionExpert")
    suspend fun getSolutionExpertRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<SolutionExpertRankResponseBody>>

    /**
     * 取得指定解題達人排行
     */
    @RecordApi
    @GET("{path}/api/Rank/SpecificSolutionExpert")
    suspend fun getSpecificSolutionExpertRank(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberIds") memberIds: String
    ): Response<List<SolutionExpertRankResponseBody>>

    @RecordApi
    @GET("{path}/api/Notify/Get")
    suspend fun getNotify(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("updateTime") updateTime: Long,
        @Query("offsetCount") offsetCount: Int
    ): Response<List<GetNotifyResponseBody>>

    @RecordApi
    @GET("{path}/api/Notify/GetCount")
    suspend fun getCount(
        @Path("path") path: String,
        @Header("Authorization") authorization: String
    ): Response<GetNotifyCountResponseBody>

    @RecordApi
    @POST("{path}/api/Notify/ResetCount")
    suspend fun resetCount(
        @Path("path") path: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/Notify/SetRead")
    suspend fun setRead(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("type") notifyType: String,
        @Query("mergeKey") mergeKey: String,
        @Query("isNew") isNew: Boolean
    ): Response<Void>

    @RecordApi
    @POST("{path}/api/BonusPointExchange/{articleId}")
    suspend fun exchangeColumnArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @GET("{path}/api/Role")
    suspend fun getRole(
        @Header("Authorization") authorization: String,
        @Path("path") path: String
    ): Response<List<Int>>

    @RecordApi
    @GET("{path}/api/Role/Id/{communityRoleId}")
    suspend fun getMembersByRoleId(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("communityRoleId") roleId: Int
    ): Response<GetMembersByRoleResponse>

    @RecordApi
    @GET("{path}/api/Role/{otherMemberId}")
    suspend fun getRole(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("otherMemberId") memberId: Long
    ): Response<List<Int>>

    @RecordApi
    @GET("{path}/api/ExchangeCount/{memberId}")
    suspend fun getExchangeCount(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("memberId") memberId: Long
    ): Response<ExchangeCount>

    @RecordApi
    @GET("{path}/api/IsMemberSubscribe/{memberId}")
    suspend fun isMemberSubscribe(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("memberId") memberId: Long
    ): Response<ResponseBody>

    @RecordApi
    @GET("{path}/api/ColumnistVipGroup/{columnistMemberId}")
    suspend fun getColumnistVipGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("columnistMemberId") columnistMemberId: Long
    ): Response<GetColumnistVipGroupResponse>

    /**
     * 取得指定研究報告文章ID
     */
    @RecordApi
    @GET("{path}/api/StockReport")
    suspend fun getStockReportId(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("date") date: String,
        @Query("brokerId") brokerId: String,
        @Query("stockId") stockId: String
    ): Response<Int>

    /**
     * 取得指定社團資訊
     */
    @RecordApi
    @GET("{path}/api/Group/{groupId}")
    @Headers("X-Version: 2.0")
    suspend fun getGroupV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
    ): Response<Group>

    /**
     * 依角色取得會員所有社團(MemberId不帶等於取自己)
     */
    @RecordApi
    @GET("{path}/api/Group/All")
    @Headers("X-Version: 2.0")
    suspend fun getGroupsByRole(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("memberId") memberId: Long?, // Optional
        @Query("roleTypes", encoded = true) roles: String
    ): Response<List<Group>>

    /**
     * 創建社團
     */
    @RecordApi
    @POST("{path}/api/Group")
    @Headers("X-Version: 2.0")
    suspend fun createGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body body: GroupManipulation
    ): Response<InsertedId>

    /**
     * 更新社團資訊
     */
    @RecordApi
    @PUT("{path}/api/Group/{groupId}")
    @Headers("X-Version: 2.0")
    suspend fun updateGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Body body: GroupManipulation
    ): Response<Void>

    /**
     * 解散社團(鎖權限：社長)
     */
    @RecordApi
    @DELETE("{path}/api/Group/{groupId}")
    @Headers("X-Version: 2.0")
    suspend fun deleteGroupV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<Void>

    /**
     * 增加社團的板(鎖權限:社長)
     */
    @RecordApi
    @POST("{path}/api/Group/{groupId}/Board")
    @Headers("X-Version: 2.0")
    suspend fun createGroupBoard(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Body body: BoardManipulation
    ): Response<InsertedId>

    /**
     * 修改看板
     */
    @RecordApi
    @PUT("{path}/api/Group/Board/{boardId}")
    @Headers("X-Version: 2.0")
    suspend fun updateGroupBoard(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("boardId") boardId: Long,
        @Body body: BoardManipulation
    ): Response<Void>

    /**
     * 取得社團所有看板
     */
    @RecordApi
    @GET("{path}/api/Group/{groupId}/Board/All")
    @Headers("X-Version: 2.0")
    suspend fun getGroupBoards(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
    ): Response<List<Board>>

    /**
     * 取得特定看板資訊
     */
    @RecordApi
    @GET("{path}/api/Group/Board/{boardId}")
    @Headers("X-Version: 2.0")
    suspend fun getGroupBoard(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("boardId") boardId: Long,
    ): Response<BoardSingle>

    /**
     * 刪除社團看板(鎖權限：幹部以上)
     */
    @RecordApi
    @DELETE("{path}/api/Group/Board/{boardId}")
    @Headers("X-Version: 2.0")
    suspend fun deleteGroupBoard(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("boardId") boardId: Long,
    ): Response<Void>

    /**
     * 取得社團是否有未察看的待審用戶
     */
    @RecordApi
    @GET("{path}/api/Group/{groupId}/HasNewPending")
    @Headers("X-Version: 2.0")
    suspend fun hasNewGroupPending(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
    ): Response<ResponseBody>

    /**
     * 取得該社員在社團的所有角色
     */
    @RecordApi
    @GET("{path}/api/GroupMember/{groupId}/{memberId}/Role")
    @Headers("X-Version: 2.0")
    suspend fun getGroupMemberRoles(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Path("memberId") memberId: Long
    ): Response<MemberRoles>

    /**
     * 設定社團身份(鎖權限：幹部以上)
     */
    @RecordApi
    @PUT("{path}/api/GroupMember/{groupId}/{memberId}/Role")
    @Headers("X-Version: 2.0")
    suspend fun updateGroupMemberRoles(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Path("memberId") memberId: Long,
        @Body roles: List<Int>
    ): Response<Void>

    /**
     * 取得社團成員列表
     */
    @RecordApi
    @GET("{path}/api/GroupMember/{groupId}")
    @Headers("X-Version: 2.0")
    suspend fun getGroupMembers(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("roleTypes", encoded = true) roles: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<GroupMember2>>

    /**
     * 離開社團
     */
    @RecordApi
    @DELETE("{path}/api/GroupMember/{groupId}")
    @Headers("X-Version: 2.0")
    suspend fun leaveGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<Void>

    /**
     * 取得社長及幹部清單
     */
    @RecordApi
    @GET("{path}/api/GroupMember/{groupId}/Admins")
    @Headers("X-Version: 2.0")
    suspend fun getGroupAdmins(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<Admins>

    /**
     * 搜尋社員
     */
    @RecordApi
    @GET("{path}/api/GroupMember/{groupId}/Search")
    @Headers("X-Version: 2.0")
    suspend fun searchGroupMember(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("keyword") keyword: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<List<com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember>>

    /**
     * 申請加入社團
     */
    @RecordApi
    @POST("{path}/api/GroupMember/{groupId}/Join")
    @Headers("X-Version: 2.0")
    suspend fun joinGroup(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Body body: JoinGroupRequest
    ): Response<Void>

    /**
     * 取得審核成員列表(鎖權限：幹部以上)
     */
    @RecordApi
    @GET("{path}/api/GroupMember/{groupId}/Pending")
    @Headers("X-Version: 2.0")
    suspend fun getGroupPendingRequests(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("timestamp") timestamp: Long,
        @Query("fetch") fetch: Int = 20
    ): Response<PendingRequests>

    /**
     * 搜尋審核中的社員(鎖權限：幹部以上)
     */
    @RecordApi
    @GET("{path}/api/GroupMember/{groupId}/SearchPending")
    @Headers("X-Version: 2.0")
    suspend fun searchGroupPendingRequests(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Query("keyword") keyword: String,
        @Query("timestamp") timestamp: Long?,
        @Query("fetch") fetch: Int = 20
    ): Response<PendingRequests>

    /**
     * 審核成員加入(鎖權限：幹部以上)
     */
    @RecordApi
    @POST("{path}/api/GroupMember/{groupId}/Approve")
    @Headers("X-Version: 2.0")
    suspend fun approvalGroupRequest(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Body body: List<Approval>
    ): Response<Void>

    /**
     * 踢出成員(鎖權限：幹部以上)
     */
    @RecordApi
    @DELETE("{path}/api/GroupMember/{groupId}/{memberId}")
    @Headers("X-Version: 2.0")
    suspend fun kickGroupMember(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long,
        @Path("memberId") memberId: Long
    ): Response<Void>

    /**
     * 對社團看板發文
     */
    @RecordApi
    @POST("{path}/api/GroupArticle/Board/{boardId}/normal")
    @Headers("X-Version: 2.0")
    suspend fun createGroupArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("boardId") boardId: Long,
        @Body requestBody: Content.Article.General
    ): Response<CreateArticleResponseBody>

    /**
     * 刪除社團文章
     */
    @RecordApi
    @DELETE("{path}/api/GroupArticle/{articleId}")
    @Headers("X-Version: 2.0")
    @Deprecated("待服務實作完成後，請使用deleteArticleV2")
    suspend fun deleteGroupArticle(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    /**
     * 刪除看板文章留言
     */
    @RecordApi
    @DELETE("{path}/api/GroupArticle/{articleId}/Comment/{commentId}")
    @Headers("X-Version: 2.0")
    @Deprecated("請使用deleteCommentV2")
    suspend fun deleteGroupArticleComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: Long,
        @Path("commentId") commentId: Long
    ): Response<Void>

    /**
     * 取得用戶可以進入的所有看板 id
     */
    @RecordApi
    @GET("{path}/api/Group/Board/All")
    @Headers("X-Version: 2.0")
    suspend fun getAvailableBoardIds(
        @Header("Authorization") authorization: String,
        @Path("path") path: String
    ): Response<AvailableBoardIds>

    /**
     * 取得會員在社團內的推播設定
     */
    @RecordApi
    @GET("{path}/api/GroupNotification/PushSetting/Group/{groupId}")
    @Headers("X-Version: 2.0")
    suspend fun getGroupPushSetting(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("groupId") groupId: Long
    ): Response<GroupPushSetting>

    /**
     * 設定會員在社團內的推播
     */
    @RecordApi
    @PUT("{path}/api/GroupNotification/PushSetting")
    @Headers("X-Version: 2.0")
    suspend fun setGroupPushSetting(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body body: GroupPushSettingRequest
    ): Response<Void>

    /**
     * 取得會員的被評價資訊統計
     */
    @RecordApi
    @GET("{path}/api/Rating/{memberId}")
    suspend fun getMemberRatingCounter(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("memberId") memberId: Long
    ): Response<MemberRatingCounter>

    /**
     * 取得指定評價
     */
    @RecordApi
    @GET("{path}/api/Rating/Review/{creatorId}/{memberId}")
    suspend fun getRatingComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("creatorId") creatorId: Long,
        @Path("memberId") memberId: Long
    ): Response<RatingComment>

    /**
     * 取得指定會員的被評價清單
     */
    @RecordApi
    @GET("{path}/api/Rating/Reviews/{memberId}")
    suspend fun getMemberRatingComments(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("memberId") memberId: Long,
        @Query("sortType") sortType: Int,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<List<OthersRatingComment>>

    /**
     * 滿分為5, 評論字數不可多於200
     */
    @RecordApi
    @PUT("{path}/api/Rating/Review")
    suspend fun reviewUser(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Body body: ReviewRequest
    ): Response<String>

    @RecordApi
    @GET("{path}/api/Columnist/All")
    suspend fun getColumnistAll(
        @Header("Authorization") authorization: String,
        @Path("path") path: String
    ): Response<List<Columnist>>


    /**
     * 取得文章資訊v2
     */
    @RecordApi
    @GET("{path}/api/Article/{articleId}")
    @Headers("X-Version: 2.0")
    suspend fun getArticleV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String
    ): Response<ArticleResponseBodyV2>

    /**
     * 取得留言V2(可訪客)
     */
    @RecordApi
    @GET("{path}/api/Article/{articleId}/Comments")
    @Headers("X-Version: 2.0")
    suspend fun getCommentV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String,
        @Query("startCommentIndex") startCommentIndex: Long?,
        @Query("fetch") fetch: Int?
    ): Response<GetCommentsResponseBody>

    /**
     * 取得指定 index 的留言
     */
    @RecordApi
    @GET("{path}/api/Article/CommentsByIndex")
    @Headers("X-Version: 2.0")
    suspend fun getCommentsByIndex(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("articleId") articleId: String,
        @Query("commentIndex") commentIndex: String
    ): Response<GetCommentsResponseBody>

    /**
     * 發留言v2
     */
    @RecordApi
    @POST("{path}/api/Article/{articleId}/Comment")
    @Headers("X-Version: 2.0")
    suspend fun createCommentV2(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String,
        @Body body: CreateCommentRequestBodyV2
    ): Response<CreateCommentResponseBodyV2>

    /**
     * 隱藏留言
     */
    @RecordApi
    @PUT("{path}/api/Article/{articleId}/Hide")
    @Headers("X-Version: 2.0")
    suspend fun hideComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String,
    ): Response<HideCommentResponseBody>

    /**
     * 取得單一留言
     */
    @RecordApi
    @GET("{path}/api/Article/{articleId}")
    @Headers("X-Version: 2.0")
    suspend fun getSingleComment(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Path("articleId") articleId: String
    ): Response<CommentResponseBodyV2>

    /**
     * 取得個人化推薦文章
     */
    @RecordApi
    @GET("{path}/api/Channel/GetRecommendation/Recommendation")
    suspend fun getRecommendation(
        @Header("Authorization") authorization: String,
        @Path("path") path: String,
        @Query("offset") offset: Int,
        @Query("fetch") fetch: Int
    ): Response<GetRecommendationResponse>
}
