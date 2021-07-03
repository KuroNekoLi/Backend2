package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.react.ReactCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import retrofit2.Response
import retrofit2.http.*

interface ForumOceanService {

    /**
     * 發一般文
     *
     */
    @RecordApi
    @POST("ForumOcean/api/Article/Create")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.General
    ): Response<CreateArticleResponseBody>

    /**
     * 發社團文
     *
     */
    @RecordApi
    @POST("ForumOcean/api/Article/Create")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.Group
    ): Response<CreateArticleResponseBody>

    /**
     * 發轉推文
     *
     */
    @RecordApi
    @POST("ForumOcean/api/Article/Create")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Article.Shared
    ): Response<CreateArticleResponseBody>

    /**
     * 發問答文章
     */
    @RecordApi
    @POST("ForumOcean/api/Article/CreateQuestion")
    suspend fun createQuestion(
        @Header("Authorization") authorization: String,
        @Body requestBody: Content.Question
    ): Response<CreateQuestionResponseBody>

    /**
     * 取得文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.GeneralArticleResponseBody>

    /**
     * 取得問答文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getQuestionArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.QuestionArticleResponseBody>

    /**
     * 取得新聞文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getNewsArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.NewsArticleResponseBody>

    /**
     * 取得訊號文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getSignalArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.SignalArticleResponseBody>

    /**
     * 取得社團文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getGroupArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.GroupArticleResponseBody>

    /**
     * 取得轉推文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getSharedArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.SharedArticleResponseBody>

    /**
     * 取得文章資訊
     *
     */
    @RecordApi
    @GET("ForumOcean/api/Article/Get/{articleId}")
    suspend fun getUnknownArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<ArticleResponseBody.UnknownArticleResponseBody>

    /**
     * 修改文章資訊
     */
    @RecordApi
    @PUT("ForumOcean/api/Article/Update/{articleId}")
    suspend fun updateArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Body body: UpdateArticleRequestBody
    ): Response<Void>

    /**
     * 刪除文章
     */
    @RecordApi
    @DELETE("ForumOcean/api/Article/Delete/{articleId}")
    suspend fun deleteArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Channel/GetChannelsArticleByWeight")
    suspend fun getChannelsArticleByWeight(
        @Header("Authorization") authorization: String,
        @Query("channelNames") channelNameList: String,
        @Query("startScore") startScore: Long,
        @Query("count") count: Int
    ): Response<List<ArticleResponseBody.UnknownArticleResponseBody>>

    @RecordApi
    @POST("ForumOcean/api/Collection/Create/{articleId}")
    suspend fun createCollection(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/Collection/Delete/{articleId}")
    suspend fun deleteCollection(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @POST("ForumOcean/api/Comment/Create/{articleId}")
    suspend fun createComment(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Body body: CreateCommentRequestBody
    ): Response<CreateCommentResponseBody>

    @RecordApi
    @GET("ForumOcean/api/Comment/Get/{articleId}")
    suspend fun getComment(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("commentId") commentId: Long?,
        @Query("offsetCount") offsetCount: Int?
    ): Response<List<CommentResponseBody>>

    @RecordApi
    @GET("ForumOcean/api/Comment/GetWithIds/{articleId}")
    suspend fun getCommentWithId(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("commentIds") commentIds: String
    ): Response<List<CommentResponseBody>>

    @RecordApi
    @PUT("ForumOcean/api/Comment/Update/{articleId}/{commentId}")
    suspend fun updateComment(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Path("commentId") commentId: Long,
        @Body body: UpdateCommentRequestBody
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/Comment/Delete/{articleId}/{commentIndex}")
    suspend fun deleteComment(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Int
    ): Response<Void>

    @RecordApi
    @POST("ForumOcean/api/CommentInteractive/Reaction/{articleId}/{commentIndex}")
    suspend fun reactComment(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Int,
        @Body body: ReactCommentRequestBody
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/CommentInteractive/GetReactionDetail/{articleId}/{commentIndex}")
    suspend fun getReactionDetail(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Int
    ): Response<Map<String?, List<Long>?>>

    @RecordApi
    @DELETE("ForumOcean/api/CommentInteractive/RemoveReaction/{articleId}/{commentIndex}")
    suspend fun removeCommentReaction(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Path("commentIndex") commentIndex: Int,
        @Query("originalReactionType") originalReactionType: Int
    ): Response<Void>

    @RecordApi
    @POST("ForumOcean/api/Interactive/Reaction/{articleId}")
    suspend fun createArticleReaction(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("reactionType") reactionType: Int
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Interactive/GetReactionDetail/{articleId}")
    suspend fun getArticleReactionDetail(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("reactions") reactions: String,
        @Query("count") count: Int
    ): Response<Map<String?, List<Long>?>>

    @RecordApi
    @DELETE("ForumOcean/api/Interactive/RemoveReaction/{articleId}")
    suspend fun deleteArticleReaction(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @POST("ForumOcean/api/Interactive/Interest/{articleId}")
    suspend fun createArticleInterest(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @POST("ForumOcean/api/Interactive/Donate/{articleId}")
    suspend fun createArticleDonate(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("donateValue") donateValue: Int
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Interactive/GetDonate/{articleId}")
    suspend fun getArticleDonate(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Map<Long?, Int?>>

    @RecordApi
    @GET("ForumOcean/api/Group/GetGroup/{groupId}")
    suspend fun getGroup(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long
    ): Response<GroupResponseBody>

    @RecordApi
    @GET("ForumOcean/api/Group/GetOwnerHaveGroups")
    suspend fun getUserOwnGroup(
        @Header("Authorization") authorization: String,
        @Query("ownerId") ownerId: Long
    ): Response<List<GroupResponseBody>>

    @RecordApi
    @GET("ForumOcean/api/Group/GetMemberBelongGroups")
    suspend fun getMemberBelongGroups(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<List<GroupResponseBody>>

    @RecordApi
    @POST("ForumOcean/api/Group/Create")
    suspend fun createGroup(
        @Header("Authorization") authorization: String,
        @Query("groupName") groupName: String
    ): Response<CreateGroupResponseBody>

    @RecordApi
    @PUT("ForumOcean/api/Group/Update/{groupId}")
    suspend fun updateGroup(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Body body: UpdateGroupRequestBody
    ): Response<Void>

    @RecordApi
    @PUT("ForumOcean/api/Group/TransferOwner/{groupId}")
    suspend fun transferGroup(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/Group/Delete/{groupId}")
    suspend fun deleteGroup(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long
    ): Response<Void>

    @RecordApi
    @POST("ForumOcean/api/GroupMember/Join/{groupId}")
    suspend fun join(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Query("reason") reason: String
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/GroupMember/GetMembers/{groupId}")
    suspend fun getMembers(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Query("includeManagerInfo") includeManagerInfo: Boolean
    ): Response<List<GroupMember>>

    @RecordApi
    @GET("ForumOcean/api/GroupMember/GetApprovals/{groupId}")
    suspend fun getApprovals(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long
    ): Response<List<GroupPendingApproval>>

    @RecordApi
    @POST("ForumOcean/api/GroupMember/Approval/{groupId}")
    suspend fun approval(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long,
        @Query("isAgree") isAgree: Boolean
    ): Response<Void>

    @RecordApi
    @PUT("ForumOcean/api/GroupMember/ChangeGroupMemberPosition/{groupId}")
    suspend fun changeGroupMemberPosition(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long,
        @Query("position") position: Int
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/GroupMember/Kick/{groupId}")
    suspend fun kick(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/GroupMember/Leave/{groupId}")
    suspend fun leave(
        @Header("Authorization") authorization: String,
        @Path("groupId") groupId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/GroupArticle/Delete/{articleId}")
    suspend fun deleteGroupArticle(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Official/GetOfficials")
    suspend fun getOfficials(
        @Header("Authorization") authorization: String,
        @Query("officialIds") officialIds: String
    ): Response<List<OfficialChannelInfo>>

    @RecordApi
    @GET("ForumOcean/api/OfficialSubscriber/GetOfficialSubscribedCount/{officialId}")
    suspend fun getOfficialSubscribedCount(
        @Header("Authorization") authorization: String,
        @Path("officialId") officialId: Long
    ): Response<GetOfficialSubscribedCountResponseBody>

    @RecordApi(isLogRequestBody = false)
    @GET("ForumOcean/api/OfficialSubscriber/GetSubscribedCount")
    suspend fun getSubscribedCount(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<GetSubscribedCountResponseBody>

    @RecordApi(isLogRequestBody = false)
    @GET("ForumOcean/api/OfficialSubscriber/GetSubscribeds")
    suspend fun getSubscribed(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<List<Int>>

    @RecordApi
    @POST("ForumOcean/api/OfficialSubscriber/Subscribe/{officialId}")
    suspend fun subscribe(
        @Header("Authorization") authorization: String,
        @Path("officialId") officialId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/OfficialSubscriber/Unsubscribe/{officialId}")
    suspend fun unsubscribe(
        @Header("Authorization") authorization: String,
        @Path("officialId") officialId: Long
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/OfficialSubscriber/UnsubscribeAll")
    suspend fun unsubscribeAll(
        @Header("Authorization") authorization: String
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Relationship/GetFollowingList/{memberId}")
    suspend fun getFollowingList(
        @Header("Authorization") authorization: String,
        @Path("memberId") memberId: Long
    ): Response<List<Long>>

    @RecordApi
    @GET("ForumOcean/api/Relationship/GetFollowers/{memberId}")
    suspend fun getFollowers(
        @Header("Authorization") authorization: String,
        @Path("memberId") memberId: Long
    ): Response<List<Long>>

    @RecordApi(isLogRequestBody = false)
    @POST("ForumOcean/api/Relationship/Follow")
    suspend fun follow(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi(isLogRequestBody = false)
    @DELETE("ForumOcean/api/Relationship/Unfollow")
    suspend fun unfollow(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi(isLogRequestBody = false)
    @POST("ForumOcean/api/Relationship/Block")
    suspend fun block(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi(isLogRequestBody = false)
    @DELETE("ForumOcean/api/Relationship/Unblock")
    suspend fun unblock(
        @Header("Authorization") authorization: String,
        @Query("memberId") memberId: Long
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Relationship/GetBlockingList")
    suspend fun getBlockingList(
        @Header("Authorization") authorization: String
    ): Response<List<Long>>

    @RecordApi
    @GET("ForumOcean/api/Relationship/GetBlockers")
    suspend fun getBlockers(
        @Header("Authorization") authorization: String
    ): Response<List<Long>>

    @RecordApi
    @POST("ForumOcean/api/Report/Create/{articleId}")
    suspend fun createReport(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("reasonType") reasonType : Int
    ): Response<Void>

    @RecordApi
    @DELETE("ForumOcean/api/Report/Delete/{articleId}")
    suspend fun deleteReport(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<Void>

    @RecordApi(isLogRequestBody = false)
    @GET("ForumOcean/api/Support/GetMemberIds")
    suspend fun getMemberIds(
            @Header("Authorization") authorization: String,
            @Query("channelIds") channelIds : String
    ): Response<List<ChannelIdAndMemberId>>

    @RecordApi(isLogRequestBody = false)
    @GET("ForumOcean/api/Support/GetChannelIds")
    suspend fun getChannelIds(
            @Header("Authorization") authorization: String,
            @Query("memberIds") memberIds : String
    ): Response<List<ChannelIdAndMemberId>>

    @RecordApi
    @POST("ForumOcean/api/Vote/Create/{articleId}")
    suspend fun createVote(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long,
        @Query("optionIndex") optionIndex : Int
    ): Response<Void>

    @RecordApi
    @GET("ForumOcean/api/Vote/GetCurrentVotes/{articleId}")
    suspend fun getCurrentVote(
        @Header("Authorization") authorization: String,
        @Path("articleId") articleId: Long
    ): Response<List<VoteInfo>>
}