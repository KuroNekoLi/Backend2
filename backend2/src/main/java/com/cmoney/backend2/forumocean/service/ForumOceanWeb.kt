package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.report.create.ReasonType
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo

interface ForumOceanWeb {

    //region Article 文章

    /**
     * 發文
     *
     * @param body 發文章的資訊
     * @return 文章Id
     */
    suspend fun createArticle(body: Content.Article): Result<CreateArticleResponseBody>

    /**
     * 發問答文章
     *
     * @param body 發問答文章的資訊
     * @return 問答文章Id
     */
    suspend fun createQuestion(body: Content.Question): Result<CreateQuestionResponseBody>

    /**
     * 取得一般文章
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getArticle(articleId: Long): Result<ArticleResponseBody.GeneralArticleResponseBody>

    /**
     * 取得問答文章
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getQuestionArticle(articleId: Long): Result<ArticleResponseBody.QuestionArticleResponseBody>

    /**
     * 取得社團文章
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getGroupArticle(articleId: Long): Result<ArticleResponseBody.GroupArticleResponseBody>

    /**
     * 取得轉推文章
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getSharedArticle(articleId: Long): Result<ArticleResponseBody.SharedArticleResponseBody>

    /**
     * 取得訊號文章
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getSignalArticle(articleId: Long): Result<ArticleResponseBody.SignalArticleResponseBody>

    /**
     * 取得新聞文章
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getNewsArticle(articleId: Long): Result<ArticleResponseBody.NewsArticleResponseBody>

    /**
     * 取得文章(不確定文章類型)
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getUnknownArticle(articleId: Long): Result<ArticleResponseBody.UnknownArticleResponseBody>

    /**
     * 更新文章資訊
     *
     * @param articleId 文章Id
     * @return 成功不回傳任何資訊
     */
    suspend fun updateArticle(articleId: Long, updateHelper: UpdateArticleHelper): Result<Unit>


    /**
     * 移除文章
     *
     * @param articleId 文章Id
     * @return 成功不回傳任何資訊
     */
    suspend fun deleteArticle(articleId: Long): Result<Unit>

    //endregion

    //region Channel 頻道

    /**
     * 取得指定使用者的統計資訊
     *
     * @param memberIdList 會員Id
     * @return
     */
    suspend fun getMemberStatistics(memberIdList: List<Long>): Result<List<GetMemberStatisticsResponseBody>>

    /**
     * 取得頻道文章清單(by weight) 適用於常變動的清單
     *
     * @param channelNameBuilderList
     * @param weight 權重
     * @param count 取得筆數(正數往舊的取N筆，負數往新的取N筆)
     */
    suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        weight: Long,
        count: Int
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>>

    //endregion

    //region Collection 收藏

    /**
     * 收藏文章
     *
     * @param articleId 收藏文章Id
     */
    suspend fun createCollection(articleId: Long): Result<Unit>

    /**
     * 取消收藏文章
     *
     * @param articleId 取消文章Id
     */
    suspend fun deleteCollection(articleId: Long): Result<Unit>


    //endregion

    //region Comment 回文

    /**
     * 對指定主文發一篇回文
     * @param articleId 指定主文Id
     * @return 回文Id
     */
    suspend fun createComment(
        articleId: Long,
        text: String?,
        multiMedia: List<MediaType>?,
        position: Any?
    ): Result<CreateCommentResponseBody>

    /**
     * 取得指定主文的回文清單
     *
     * @param articleId 指定主文Id
     * @param commentId 回文Id
     * @param offsetCount 取得回文偏移數量
     * @return 回文清單
     */
    suspend fun getComment(
        articleId: Long,
        commentId: Long?,
        offsetCount: Int?
    ): Result<List<CommentResponseBody>>

    /**
     * 取得指定主文的回文清單 使用回文Id指定取得清單
     *
     * @param articleId 指定主文Id
     * @param commentIds 回文Id清單
     * @return
     */
    suspend fun getCommentWithId(
        articleId: Long,
        commentIds: List<Long>
    ): Result<List<CommentResponseBody>>

    /**
     * 取得指定主文的社團管理員回文清單
     *
     * @param articleId
     * @return
     */
    suspend fun getGroupManagerComments(
        articleId: Long
    ): Result<List<CommentResponseBody>>

    /**
     * 更新回文
     *
     * @param articleId 指定主文Id
     * @param commentId 回文Id
     * @param helper 編輯回文的工具
     * @return
     */
    suspend fun updateComment(
        articleId: Long,
        commentId: Long,
        helper: UpdateCommentHelper
    ): Result<Unit>

    /**
     * 刪除回文
     *
     * @param articleId 指定主文Id
     * @param commentIndex 回文索引
     * @return
     */
    suspend fun deleteComment(articleId: Long, commentIndex: Long): Result<Unit>

    //endregion

    //region CommentInteractive 回文互動

    /**
     * 對回文做出反應
     *
     * @param articleId 指定主文Id
     * @param commentIndex 回文索引
     * @param reactionType 反應
     * @return
     */
    suspend fun reactionComment(
        articleId: Long,
        commentIndex: Long,
        reactionType: ReactionType
    ): Result<Unit>

    /**
     * 取得指定回文的反應明細
     *
     * @param articleId 指定主文Id
     * @param commentIndex 回文索引
     * @param reactions 需要取得的反應
     * @param skipCount 跳過的數量(選項)
     * @param takeCount 取得的數項(選項)
     * @return 反應 對照 做此反應會員清單
     */
    suspend fun getReactionDetail(
        articleId: Long,
        commentIndex: Long,
        reactions: List<ReactionType>,
        skipCount: Int = 0,
        takeCount: Int = Int.MAX_VALUE
    ): Result<List<ReactionInfo>>

    /**
     * 移除對回文的反應
     *
     * @param articleId 指定主文Id
     * @param commentIndex 回文索引
     * @return
     */
    suspend fun removeReactionComment(
        articleId: Long,
        commentIndex: Long
    ): Result<Unit>

    //endregion

    //region GroupArticle 社團文章管理

    /**
     * 置頂社團文章
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun pinArticle(articleId: Long): Result<Unit>

    /**
     * 取消置頂社團文章
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun unpinArticle(articleId: Long): Result<Unit>

    /**
     * 管理者刪文
     *
     * @param articleId 文章ID
     * @return
     */
    suspend fun deleteGroupArticle(
        articleId: Long
    ): Result<Unit>

    //endregion

    //region Group 社團

    /**
     * 取得社團資訊
     *
     * @param groupId 社團Id
     * @return
     */
    suspend fun getGroup(groupId: Long): Result<GroupResponseBody>

    /**
     * 取得指定使用者持有的所有社團
     *
     * @param ownId 使用者Id
     * @return
     */
    suspend fun getUserOwnGroup(ownId: Long): Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者管理的所有社團
     *
     * @param managerId
     * @return
     */
    suspend fun getMemberManagedGroups(managerId : Long) : Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者加入的所有社團
     *
     * @param memberId 使用者Id
     * @return
     */
    suspend fun getMemberBelongGroups(memberId: Long): Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者是否加入或擁有任何社團
     *
     * @param memberId 使用者Id
     * @return
     */
    suspend fun getMemberJoinAnyGroups(memberId: Long) : Result<GetMemberJoinAnyGroupsResponseBody>

    /**
     * 創建社團
     *
     * @param groupName 社團名稱
     * @return
     */
    suspend fun createGroup(groupName: String): Result<CreateGroupResponseBody>

    /**
     * 更新社團資訊
     *
     * @return
     */
    suspend fun updateGroup(groupId: Long, body: UpdateGroupRequestBody): Result<Unit>

    /**
     * 轉讓社團
     *
     * @param groupId 社團Id
     * @param memberId 轉讓對象
     * @return
     */
    suspend fun transferGroup(groupId: Long, memberId: Long): Result<Unit>

    /**
     * 刪除社團
     *
     * @param groupId 社團Id
     * @return
     */
    suspend fun deleteGroup(groupId: Long): Result<Unit>

    //endregion

    //region GroupMember 社團成員管理

    /**
     * 申請加入社團
     *
     * @param groupId 社團Id
     * @param reason 申請加入的理由
     * @return
     */
    suspend fun join(
        groupId: Long,
        reason: String
    ): Result<Unit>

    /**
     * 取得社團成員清單
     *
     * @param groupId 社團Id
     * @param offset 偏移筆數
     * @param fetch 指定筆數
     * @param includeManagerInfo 是否包含管理者成員
     * @return 社團成員清單
     */
    suspend fun getMembers(
        groupId: Long,
        offset: Int = 0,
        fetch: Int = Int.MAX_VALUE,
        includeManagerInfo: Boolean = false
    ): Result<List<GroupMember>>

    /**
     * 取得申請加入社團待審核清單
     *
     * @param groupId 社團Id
     * @return 加入社團待審核清單
     */
    suspend fun getApprovals(
        groupId: Long
    ): Result<List<GroupPendingApproval>>

    /**
     * 審核成員加入社團
     *
     * @param groupId 社團ID
     * @param memberId 被審核人員ID
     * @param isAgree 是否同意
     * @return
     */
    suspend fun approval(
        groupId: Long,
        memberId: Long,
        isAgree: Boolean
    ): Result<Unit>

    /**
     * 設定社團身份
     *
     * @param groupId 社團ID
     * @param memberId 成員ID
     * @param position 社團職位
     * @return
     */
    suspend fun changeGroupMemberPosition(
        groupId: Long,
        memberId: Long,
        position: GroupPosition
    ): Result<Unit>

    /**
     * 踼除成員
     *
     * @param groupId 社團ID
     * @param memberId 成員ID
     * @return
     */
    suspend fun kick(
        groupId: Long,
        memberId: Long
    ): Result<Unit>

    /**
     * 離開社團
     *
     * @param groupId 社團ID
     * @return
     */
    suspend fun leave(
        groupId: Long
    ): Result<Unit>

    //endregion

    //region Interactive 文章互動

    /**
     * 對文章做出互動
     *
     * @param articleId 文章Id
     * @param type 反應類型
     * @return
     */
    suspend fun createArticleReaction(
        articleId: Long,
        type: ReactionType
    ): Result<Unit>

    /**
     * 取得文章互動詳情
     *
     * @param articleId 文章Id
     * @param reactionTypeList 想取得的互動類型清單
     * @param skipCount 跳過的數量
     * @param count 取得數量
     * @return 互動 對應 做此互動的會員清單
     */
    suspend fun getArticleReactionDetail(
        articleId: Long,
        reactionTypeList: List<ReactionType>,
        skipCount: Int = 0,
        count: Int = Int.MAX_VALUE
    ): Result<List<ReactionInfo>>

    /**
     * 移除對文章的反應
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun deleteArticleReaction(
        articleId: Long
    ): Result<Unit>

    /**
     * 對文章有興趣
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun createArticleInterest(
        articleId: Long
    ): Result<Unit>

    /**
     * 對文章做出打賞
     *
     * @param articleId 文章Id
     * @param donateValue 打賞金額
     * @return
     */
    suspend fun createArticleDonate(
        articleId: Long,
        donateValue: Int
    ): Result<Unit>

    /**
     * 取得文章打賞清單
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun getArticleDonate(
        articleId: Long
    ): Result<Map<Long?, Int?>>

    //endregion

    //region Official 官方頻道查詢

    /**
     * 取得多筆官方頻道資訊
     *
     * @param officialIds 官方頻道ID清單
     * @return
     */
    suspend fun getOfficials(
        officialIds: List<Long>
    ): Result<List<OfficialChannelInfo>>

    //endregion

    //region OfficialSubscriber 官方頻道訂閱管理

    /**
     * 取得指定官方頻道的訂閱者數量
     *
     * @param officialId 官方頻道ID
     * @return
     */
    suspend fun getOfficialSubscribedCount(
        officialId: Long
    ): Result<GetOfficialSubscribedCountResponseBody>

    /**
     * 取得使用者訂閱的官方頻道數量
     *
     * @param memberId 使用者Id
     * @return
     */
    suspend fun getSubscribedCount(
        memberId: Long
    ): Result<GetSubscribedCountResponseBody>

    /**
     * 取得使用者訂閱的官方頻道清單
     *
     * @param memberId 使用者Id
     * @return
     */
    suspend fun getSubscribed(
        memberId: Long
    ): Result<List<Int>>

    /**
     * 訂閱官方頻道
     *
     * @param officialId 官方頻道ID
     * @return
     */
    suspend fun subscribe(
        officialId: Long
    ): Result<Unit>

    /**
     * 取消訂閱官方頻道
     *
     * @param officialId 官方頻道ID
     * @return
     */
    suspend fun unsubscribe(
        officialId: Long
    ): Result<Unit>

    /**
     * 取消訂閱全部官方頻道
     *
     * @return
     */
    suspend fun unsubscribeAll(): Result<Unit>

    //endregion

    //region Rank 排行榜

    //endregion

    //region Relationship 社群使用者關係

    /**
     * 取得指定使用者追蹤的對象清單
     *
     * @param memberId 使用者Id
     * @return 使用者追蹤的對象清單
     */
    suspend fun getFollowingList(memberId: Long): Result<List<Long>>

    /**
     * 取得指定使用者被對象追蹤的清單
     *
     * @param memberId 使用者Id
     * @return 使用者被對象追蹤的清單
     */
    suspend fun getFollowers(memberId: Long): Result<List<Long>>

    /**
     * 追蹤
     *
     * @param memberId 追蹤目標Id
     * @return
     */
    suspend fun follow(memberId: Long): Result<Unit>

    /**
     * 取消追蹤
     *
     * @param memberId 取消追蹤目標Id
     * @return
     */
    suspend fun unfollow(memberId: Long): Result<Unit>

    /**
     * 封鎖
     *
     * @param memberId 封鎖目標Id
     * @return
     */
    suspend fun block(memberId: Long): Result<Unit>

    /**
     * 取消封鎖
     *
     * @param memberId 取消封鎖目標Id
     * @return
     */
    suspend fun unblock(memberId: Long): Result<Unit>

    /**
     * 取得指定使用者封鎖的對象清單
     *
     * @return
     */
    suspend fun getBlockingList(): Result<List<Long>>

    /**
     * 取得指定使用者被對象封鎖的清單
     *
     * @return
     */
    suspend fun getBlockers(): Result<List<Long>>

    //endregion

    //region Report 檢舉API

    /**
     * 使用者檢舉文章 (主文回文Id帶null 回文帶回文Id)
     *
     * @param articleId 文章ID
     * @param reason 檢舉原因
     * @param commentId 回文Id
     * @return
     */
    suspend fun createReport(articleId: Long, reason: ReasonType, commentId: Long?): Result<Unit>

    /**
     * 刪除指定使用者檢舉文章的記錄
     *
     * @param articleId 文章ID
     * @return
     */
    suspend fun deleteReport(articleId: Long, commentId: Long?): Result<Unit>

    //endregion

    //region Support 輔助功能

    /**
     * 取得頻道ID對應的會員ID
     *
     * @param channelIdList 頻道ID
     * @return
     */
    suspend fun getMemberIds(channelIdList: List<Long>): Result<List<ChannelIdAndMemberId>>

    /**
     * 取得會員ID對應的頻道ID
     *
     * @param memberIdList 會員ID
     * @return
     */
    suspend fun getChannelIds(memberIdList: List<Long>): Result<List<ChannelIdAndMemberId>>

    //endregion

    //region Vote 投票功能控制器

    /**
     * 新增投票
     *
     * @param articleId 文章Id
     * @param optionIndex 選項編號(從0開始)
     * @return
     */
    suspend fun createVote(articleId: Long, optionIndex: Int): Result<Unit>

    /**
     * 取得投票
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun getCurrentVote(articleId: Long): Result<List<VoteInfo>>

    //endregion
}