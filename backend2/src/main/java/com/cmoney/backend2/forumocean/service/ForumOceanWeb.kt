package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.IUpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.update.IUpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.group.v2.AdminsDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.ApprovalDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.AvailableBoardIds
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardDTOSingle
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardManipulationDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupManipulationDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupMemberDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupMemberDTOX
import com.cmoney.backend2.forumocean.service.api.group.v2.JoinGroupRequestDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.MemberRolesDTO
import com.cmoney.backend2.forumocean.service.api.group.v2.PendingRequestsDTO
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
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.relationship.getrelationshipwithme.RelationshipWithMe
import com.cmoney.backend2.forumocean.service.api.report.create.ReasonType
import com.cmoney.backend2.forumocean.service.api.role.Role
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo

interface ForumOceanWeb {

    //region Article 文章

    /**
     * 查詢是否被禁言
     *
     * @return 是否被禁言
     */
    suspend fun getBanState(): Result<GetBanStateResponseBody>

    /**
     * 發個人文章(專欄文章or筆記文)
     *
     * @param body 發個人文章的資訊
     * @return 個人文章id
     */
    suspend fun createPersonalArticle(body: Content.PersonalArticle): Result<CreatePersonalArticleResponseBody>

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
     * 取得個人文章(專欄文章/筆記)
     *
     * @param articleId 文章Id
     * @return 文章資訊
     */
    suspend fun getPersonalArticle(articleId: Long): Result<ArticleResponseBody.PersonalArticleResponseBody>

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
     * @param updateHelper 建立修改文章的requestBody
     * @return 成功不回傳任何資訊
     */
    suspend fun updateArticle(articleId: Long, updateHelper: IUpdateArticleHelper): Result<Unit>


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
     * @param channelNameBuilderList 文章的ChannelName
     * @param weight 權重
     * @param count 取得筆數(正數往舊的取N筆，負數往新的取N筆)
     */
    suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        weight: Long,
        count: Int
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>>

    /**
     * 取得頻道文章清單(by weight) 適用於常變動的清單(First page)
     *
     * @param channelNameBuilderList 文章的ChannelName
     * @param count 取得筆數(正數往舊的取N筆，負數往新的取N筆)
     *
     * @see getChannelsArticleByWeight
     */
    suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
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
     * @param text content
     * @param multiMedia ContentType
     * @param position
     * @return 回文Id
     */
    suspend fun createComment(
        articleId: Long,
        text: String?,
        multiMedia: List<MediaType>?,
        position: Any?
    ): Result<CreateCommentResponseBody>

    /**
     * 對指定Group主文發一篇回文
     * @param articleId 指定主文Id
     * @param text content
     * @param multiMedia ContentType
     * @param position
     * @return 回文Id
     */
    suspend fun createGroupArticleComment(
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
        helper: IUpdateCommentHelper
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
        skipCount: Int,
        takeCount: Int
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
     * 關鍵字搜尋社團(沒有處理查詢使用者社團身份 故position欄位固定為null)
     *
     * @param keyword 關鍵字
     * @param offset 偏移數量
     * @param fetch 查詢數量
     * @return
     */
    suspend fun getGroupsByKeyword(
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者持有的所有社團
     *
     * @param ownId 使用者Id
     * @param offset 起始index
     * @param fetch 取得數量
     * @param positions 取得的社團職位
     * @param includeAppGroup 是否包含app的社團
     * @return
     */
    suspend fun getGroupsByPosition(
        ownId: Long,
        offset: Int,
        fetch: Int,
        positions: List<GroupPosition>,
        includeAppGroup: Boolean = false
    ): Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者管理的所有社團
     *
     * @param memberId 使用者Id
     * @param offset 起始index
     * @param fetch 取得數量
     * @param includeAppGroup 是否包含app的社團
     */
    suspend fun getMemberManagedGroups(
        memberId: Long,
        offset: Int,
        fetch: Int,
        includeAppGroup: Boolean = false
    ): Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者加入的所有社團
     *
     * @param memberId 使用者Id
     * @param offset 起始index
     * @param fetch 取得數量
     * @param includeAppGroup 是否包含app的社團
     * @return
     */
    suspend fun getMemberBelongGroups(
        memberId: Long,
        offset: Int,
        fetch: Int,
        includeAppGroup: Boolean = false
    ): Result<List<GroupResponseBody>>

    /**
     * 取得指定使用者是否加入或擁有任何社團
     *
     * @param memberId 使用者Id
     * @return
     */
    suspend fun getMemberJoinAnyGroups(memberId: Long): Result<GetMemberJoinAnyGroupsResponseBody>

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
     * @param groupId 社團id
     * @param body 社團body
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
     * @param reason 申請加入的理由(不能為空字串)
     * @return
     */
    suspend fun join(
        groupId: Long,
        reason: String
    ): Result<Unit>

    /**
     * 申請加入社團
     *
     * @param groupId 社團Id
     * @return
     */
    suspend fun join(
        groupId: Long
    ): Result<Unit>

    /**
     * 取得社團成員清單
     *
     * @param groupId 社團Id
     * @param offset 偏移筆數
     * @param fetch 指定筆數
     * @param position 身份
     * @return 社團成員清單
     */
    suspend fun getMembers(
        groupId: Long,
        offset: Int,
        fetch: Int,
        position: List<GroupPosition>
    ): Result<List<GroupMember>>

    /**
     * 取得申請加入社團待審核清單
     *
     * @param groupId 社團Id
     * @param offset 偏移數量
     * @param fetch 查詢數量
     * @return 加入社團待審核清單
     */
    suspend fun getApprovals(
        groupId: Long,
        offset: Int,
        fetch: Int
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
        skipCount: Int,
        count: Int
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
     * @param offset 偏移數量
     * @param fetch 查詢數量
     * @return
     */
    suspend fun getArticleDonate(
        articleId: Long,
        offset: Int,
        fetch: Int
    ): Result<List<DonateInfo>>

    //endregion

    //region Notify 通知API

    /**
     * 取得使用者通知
     *
     * @param updateTime  指定通知的時間(不包含) (unixTime 單位:毫秒)
     * @param offsetCount 取得通知筆數
     * @return
     */
    suspend fun getNotify(
        updateTime: Long,
        offsetCount: Int = 5
    ): Result<List<GetNotifyResponseBody>>

    /**
     * 取得使用者通知數量
     *
     */
    suspend fun getNotifyCount(): Result<GetNotifyCountResponseBody>

    /**
     * 重設使用者通知數量
     */
    suspend fun resetNotifyCount(): Result<Unit>

    /**
     * 設定通知為已讀
     *
     * @param notifyType 通知類型
     * @param mergeKey 合併主鍵
     * @param isNew 是否為新通知
     * @return
     */
    suspend fun setNotifyRead(
        notifyType: String,
        mergeKey: String,
        isNew: Boolean
    ): Result<Unit>

    //endregion

    //region NotifySetting 通知設定API

    /**
     * 取得預設通知設定
     *
     * @return
     */
    suspend fun getPushDefaultSetting(): Result<List<NotifyPushSetting>>

    /**
     * 取得使用者通知設定
     *
     * @return
     */
    suspend fun getUserNotifySetting(): Result<List<NotifyPushSetting>>

    /**
     * 設定通知開關
     *
     * @param notifyType 通知類型
     * @param subType 通知子類型
     * @param enable 通知是否啟用
     * @return
     */
    suspend fun setNotifySetting(
        notifyType: String,
        subType: String = "",
        enable: Boolean
    ): Result<Unit>

    //endregion

    //region Official 官方頻道查詢

    /**
     * 取得官方頻道資訊
     *
     * @param offset 偏移數量
     * @param fetch 拿取數量
     * @return
     */
    suspend fun getOfficials(
        offset: Int,
        fetch: Int
    ): Result<List<OfficialChannelInfo>>

    /**
     * 取得多筆官方頻道資訊
     *
     * @param officialIds 官方頻道ID清單
     * @return
     */
    suspend fun getOfficialsByIds(
        officialIds: List<Long>
    ): Result<List<OfficialChannelInfo>>

    /**
     * 搜尋官方頻道
     *
     * @param keyword 關鍵字
     * @param offset 偏移數量
     * @param fetch 拿取數量
     * @return
     */
    suspend fun getOfficialsByKeyWord(
        keyword: String,
        offset: Int,
        fetch: Int
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
     * @param offset 偏移數量
     * @param fetch 查詢數量
     * @return
     */
    suspend fun getSubscribed(
        memberId: Long,
        offset: Int,
        fetch: Int
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

    /**
     * 取得個股排行(不含美股)
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     */
    suspend fun getCommodityRank(
        offset: Int,
        fetch: Int
    ): Result<List<GetCommodityRankResponseBody>>

    /**
     * 取得美股排行
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     */
    suspend fun getUSCommodityRank(
        offset: Int,
        fetch: Int
    ): Result<List<GetCommodityRankResponseBody>>

    /**
     * 取得達人排行
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     */
    suspend fun getExpertMemberRank(
        offset: Int,
        fetch: Int
    ): Result<List<GetExpertMemberRankResponseBody>>

    /**
     * 取得指定達人排行
     * @param memberIds(用逗號分隔)
     */
    suspend fun getSpecificExpertMemberRank(memberIds: String): Result<List<GetExpertMemberRankResponseBody>>

    /**
     * 取得粉絲成長達人排行
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     */
    suspend fun getMemberFansRank(offset: Int, fetch: Int): Result<List<FansMemberRankResponseBody>>

    /**
     * 取得指定粉絲成長達人排行
     * @param memberIds(用逗號分隔)
     */
    suspend fun getSpecificMemberFansRank(memberIds: String): Result<List<FansMemberRankResponseBody>>

    /**
     * 取得解題達人排行
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     */
    suspend fun getSolutionExpertRank(
        offset: Int,
        fetch: Int
    ): Result<List<SolutionExpertRankResponseBody>>

    /**
     * 取得指定解題達人排行
     * @param memberIds(用逗號分隔)
     */
    suspend fun getSpecificSolutionExpertRank(memberIds: String): Result<List<SolutionExpertRankResponseBody>>

    //endregion

    //region Relationship 社群使用者關係

    /**
     * 取得指定使用者追蹤的對象清單
     *
     * @param memberId 使用者Id
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @return 使用者追蹤的對象清單
     */
    suspend fun getFollowingList(memberId: Long, offset: Int, fetch: Int): Result<List<Long>>

    /**
     * 取得指定使用者被對象追蹤的清單
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param memberId 使用者Id
     * @return 使用者被對象追蹤的清單
     */
    suspend fun getFollowers(memberId: Long, offset: Int, fetch: Int): Result<List<Long>>

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
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @return
     */
    suspend fun getBlockingList(offset: Int, fetch: Int): Result<List<Long>>

    /**
     * 取得指定使用者被對象封鎖的清單
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @return
     */
    suspend fun getBlockers(offset: Int, fetch: Int): Result<List<Long>>

    /**
     * 取得指定的會員清單與我目前的關係
     *
     * @param memberIdList 會員清單
     * @return
     */
    suspend fun getRelationshipWithMe(memberIdList: List<Long>): Result<List<RelationshipWithMe>>

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

    /**
     * 以關鍵字搜尋會員
     *
     * @param keyword 關鍵字
     * @param offset 偏移數量
     * @param fetch 查詢數量
     */
    suspend fun searchMembers(
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<SearchMembersResponseBody>>


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

    /**
     * P 幣兌換專欄文章
     *
     * @param articleId 文章Id
     */
    suspend fun exchangeColumnArticle(articleId: Long): Result<Unit>

    /**
     * 取得會員的社群角色
     */
    suspend fun getRole(): Result<Set<Role>>

    /**
     * 取得其他使用者的角色
     */
    suspend fun getRole(memberId: Long): Result<Set<Role>>

    /**
     * 使用者已兌換該作者文章數及上限
     *
     * @param memberId 用戶Id
     */
    suspend fun getExchangeCount(memberId: Long): Result<ExchangeCount>

    /**
     * 取得用戶是否訂閱該專欄作家
     *
     * @param memberId 用戶Id
     */
    suspend fun isMemberSubscribe(memberId: Long): Result<Boolean>

    /**
     * 取得專欄作家Vip社團資訊
     *
     * @param columnistMemberId 專欄作家Id
     */
    suspend fun getColumnistVipGroup(columnistMemberId: Long): Result<GetColumnistVipGroupResponse>

    /**
     * 取得指定研究報告文章ID
     *
     * @param date 日期(yyyyMMdd)
     * @param brokerId 券商代號
     * @param stockId 股票代號
     */
    suspend fun getStockReportId(date: String, brokerId: String, stockId: String): Result<Int>

    /**
     * 取得指定社團資訊
     *
     * GroupV2
     */
    suspend fun getGroupV2(groupId: Long): Result<GroupDTO>

    /**
     * 依角色取得會員所有社團(MemberId不帶等於取自己)
     *
     * GroupV2
     */
    suspend fun getGroupByRoles(
        memberId: Long?,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>
    ): Result<List<GroupDTO>>

    /**
     * 創建社團
     *
     * GroupV2
     * @return Id of inserted group
     */
    suspend fun createGroup(group: GroupManipulationDTO): Result<Long>

    /**
     * 更新社團資訊
     *
     * GroupV2
     */
    suspend fun updateGroup(groupId: Long, group: GroupManipulationDTO): Result<Unit>

    /**
     * 解散社團(鎖權限：社長)
     *
     * GroupV2
     */
    suspend fun dismissGroup(groupId: Long): Result<Unit>

    /**
     * 增加社團的板(鎖權限:社長)
     *
     * GroupV2
     * @return Id of inserted board
     */
    suspend fun createGroupBoard(groupId: Long, board: BoardManipulationDTO): Result<Long>

    /**
     * 修改看板
     *
     * GroupV2
     */
    suspend fun updateGroupBoard(boardId: Long, board: BoardManipulationDTO): Result<Unit>

    /**
     * 取得社團所有看板
     *
     * GroupV2
     */
    suspend fun getGroupBoards(groupId: Long): Result<List<BoardDTO>>

    /**
     * 取得特定看板資訊
     *
     * GroupV2
     */
    suspend fun getGroupBoard(boardId: Long): Result<BoardDTOSingle>

    /**
     * 刪除社團看板(鎖權限：幹部以上)
     *
     * GroupV2
     */
    suspend fun deleteGroupBoard(boardId: Long): Result<Unit>

    /**
     * 取得社團是否有未察看的待審用戶
     *
     * GroupV2
     */
    suspend fun hasNewGroupPending(groupId: Long): Result<Boolean>

    /**
     * 取得該社員在社團的所有角色
     *
     * GroupV2
     */
    suspend fun getGroupMemberRoles(groupId: Long, memberId: Long): Result<MemberRolesDTO>

    /**
     * 設定社團身份(鎖權限：幹部以上)
     *
     * GroupV2
     */
    suspend fun updateGroupMemberRoles(
        groupId: Long,
        memberId: Long,
        roleIds: List<Int>
    ): Result<Unit>

    /**
     * 取得社團成員列表
     *
     * GroupV2
     */
    suspend fun getGroupMembers(
        groupId: Long,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>,
        offset: Int,
        fetch: Int
    ): Result<List<GroupMemberDTOX>>

    /**
     * 離開社團
     *
     * GroupV2
     */
    suspend fun leaveGroup(groupId: Long): Result<Unit>

    /**
     * 取得社長及幹部清單
     *
     * GroupV2
     */
    suspend fun getGroupAdmins(groupId: Long): Result<AdminsDTO>

    /**
     * 搜尋社員
     *
     * GroupV2
     */
    suspend fun searchGroupMember(
        groupId: Long,
        keyword: String,
        offset: Int,
        fetch: Int
    ): Result<List<GroupMemberDTO>>

    /**
     * 申請加入社團
     *
     * GroupV2
     */
    suspend fun joinGroup(groupId: Long, joinGroupRequestDTO: JoinGroupRequestDTO): Result<Unit>

    /**
     * 取得審核成員列表(鎖權限：幹部以上)
     *
     * @param timestamp The page timestamp. (For pagination).
     *
     * GroupV2
     */
    suspend fun getGroupPendingRequests(
        groupId: Long,
        timestamp: Long,
        fetch: Int = 20
    ): Result<PendingRequestsDTO>

    /**
     * 搜尋審核中的社員(鎖權限：幹部以上)
     *
     * GroupV2
     */
    suspend fun searchGroupPendingRequests(
        groupId: Long,
        keyword: String,
        timestamp: Long,
        fetch: Int = 20
    ): Result<PendingRequestsDTO>

    /**
     * 審核成員加入(鎖權限：幹部以上)
     *
     * GroupV2
     */
    suspend fun approvalGroupRequest(groupId: Long, approval: List<ApprovalDTO>): Result<Unit>

    /**
     * 踢出成員(鎖權限：幹部以上)
     *
     * GroupV2
     */
    suspend fun kickGroupMember(groupId: Long, memberId: Long): Result<Unit>

    /**
     * 對社團看板發文
     */
    suspend fun createGroupArticle(
        boardId: Long,
        content: Content.Article.General
    ): Result<CreateArticleResponseBody>

    /**
     * 刪除看板文章
     */
    suspend fun deleteGroupArticle(articleId: Long): Result<Unit>

    /**
     * 刪除看板文章留言
     */
    suspend fun deleteGroupArticleComment(articleId: Long, commentId: Long): Result<Unit>

    /**
     * 取得用戶可以進入的所有看板 id
     */
    suspend fun getAvailableBoardIds(): Result<AvailableBoardIds>

    /**
     * 取得社團推播
     */
    suspend fun getGroupPushSetting(groupId: Long): Result<PushType>

    /**
     * 設定社團推播
     */
    suspend fun setGroupPushSetting(groupId: Long, pushType: PushType): Result<Unit>
}