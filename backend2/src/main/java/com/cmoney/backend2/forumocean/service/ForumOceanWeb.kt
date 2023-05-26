package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.IUpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.chatroom.GetUncheckChatRoomCountResponse
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
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
import com.cmoney.backend2.forumocean.service.api.role.Role
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.PersonalArticleType
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

interface ForumOceanWeb {

    val manager: GlobalBackend2Manager

    //region Article 文章

    /**
     * 查詢是否被禁言
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 是否被禁言
     */
    suspend fun getBanState(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/GetBanState"
    ): Result<GetBanStateResponseBody>

    /**
     * 發個人文章(專欄文章or筆記文)
     *
     * @param body 發個人文章的資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 個人文章id
     */
    suspend fun createPersonalArticle(
        body: Content.PersonalArticle,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = run {
            val articleType = when (body) {
                is Content.PersonalArticle.Columnist -> {
                    PersonalArticleType.COLUMNIST.articleType
                }
                is Content.PersonalArticle.Note -> {
                    PersonalArticleType.NOTE.articleType
                }
            }
            "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${articleType}"
        }
    ): Result<CreatePersonalArticleResponseBody>

    /**
     * 發文
     *
     * @param body 發文章的資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 文章Id
     */
    suspend fun createArticle(
        body: Content.Article,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = run {
            when (body) {
                is Content.Article.General,
                is Content.Article.Group,
                is Content.Article.Shared -> {
                    "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/Create"
                }
                is Content.Article.Column -> {
                    "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/article/columnist"
                }
            }
        }
    ): Result<CreateArticleResponseBody>

    /**
     * 發問答文章
     *
     * @param body 發問答文章的資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 問答文章Id
     */
    suspend fun createQuestion(
        body: Content.Question,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/CreateQuestion"
    ): Result<CreateQuestionResponseBody>

    /**
     * 更新文章資訊
     *
     * @param articleId 文章Id
     * @param updateHelper 建立修改文章的requestBody
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 成功不回傳任何資訊
     */
    suspend fun updateArticle(
        articleId: Long,
        updateHelper: IUpdateArticleHelper,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/Update/${articleId}"
    ): Result<Unit>


    /**
     * 刪除文章
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 成功不回傳任何資訊
     */
    @Deprecated("待服務實作完成，使用deleteArticleV2")
    suspend fun deleteArticle(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/Delete/${articleId}"
    ): Result<Unit>

    /**
     * 刪除文章V2
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun deleteArticleV2(
        articleId: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${articleId}"
    ): Result<Unit>

    //endregion

    //region Channel 頻道

    /**
     * 取得指定使用者的統計資訊
     *
     * @param memberIdList 會員Id
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getMemberStatistics(
        memberIdList: List<Long>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Member/Info"
    ): Result<List<GetMemberStatisticsResponseBody>>

    /**
     * 取得頻道文章清單(by weight) 適用於常變動的清單
     *
     * @param channelNameBuilderList 文章的ChannelName
     * @param weight 權重, null時取分頁的第一頁
     * @param count 取得筆數(正數往舊的取N筆，負數往新的取N筆)
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        weight: Long? = null,
        count: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Channel/GetChannelsArticleByWeight"
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>>

    /**
     * 取得頻道文章清單(by weight) 適用於常變動的清單(First page)
     *
     * @param channelNameBuilderList 文章的ChannelName
     * @param count 取得筆數(正數往舊的取N筆，負數往新的取N筆)
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    @Deprecated(
        message = "20230523 can use getChannelsArticleByWeight(weight = null)",
        replaceWith = ReplaceWith("this.getChannelsArticleByWeight(channelNameBuilderList = channelNameBuilderList, weight = null, count = count, domain = domain, url = url)")
    )
    suspend fun getChannelsArticleByWeight(
        channelNameBuilderList: List<IChannelNameBuilder>,
        count: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Channel/GetChannelsArticleByWeight"
    ): Result<List<ArticleResponseBody.UnknownArticleResponseBody>> = getChannelsArticleByWeight(
        channelNameBuilderList = channelNameBuilderList,
        weight = null,
        count = count,
        domain = domain,
        url = url
    )

    //endregion

    //region Collection 收藏

    /**
     * 收藏文章
     *
     * @param articleId 收藏文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun createCollection(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Collection/Create/${articleId}"
    ): Result<Unit>

    /**
     * 取消收藏文章
     *
     * @param articleId 取消文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun deleteCollection(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Collection/Delete/${articleId}"
    ): Result<Unit>


    //endregion

    //region Comment 回文

    /**
     * 取得指定主文的社團管理員回文清單
     *
     * @param articleId
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getGroupManagerComments(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Comment/GetGroupManagerComments/${articleId}"
    ): Result<List<CommentResponseBody>>

    /**
     * 更新回文
     *
     * @param articleId 指定主文Id
     * @param commentId 回文Id
     * @param helper 編輯回文的工具
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun updateComment(
        articleId: Long,
        commentId: Long,
        helper: IUpdateCommentHelper,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Comment/Update/${articleId}/${commentId}"
    ): Result<Unit>

    /**
     * 刪除回文
     *
     * @param commentId 回文Id
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun deleteCommentV2(
        commentId: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/$commentId"
    ): Result<Unit>

    //endregion

    //region CommentInteractive 回文互動

    /**
     * 取得指定回文的反應明細V2
     *
     * @param id 文章或留言
     * @param reactions 需要取得的反應
     * @param offset 跳過的數量(選項)
     * @param fetch 取得的數項(選項)
     * @return 反應 對照 做此反應會員清單
     */
    suspend fun getReactionDetailV2(
        id: String,
        reactions: List<ReactionType>,
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${id}/Emoji/Detail"
    ): Result<MemberEmojis>

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
    ): Result<List<com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember>>

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
     * 對文章/留言做出互動
     *
     * @param id 文章或留言id
     * @param type 反應類型
     * @return
     */
    suspend fun createReaction(
        id: String,
        type: ReactionType,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${id}/Emoji/${type.value}"
    ): Result<Unit>

    /**
     * 移除對文章/回覆的反應
     *
     * @param id 文章或留言Id
     * @return
     */
    suspend fun deleteReaction(
        id: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${id}/Emoji"
    ): Result<Unit>

    /**
     * 對文章有興趣
     *
     * @param articleId 文章Id
     * @return
     */
    suspend fun createArticleInterest(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Interactive/Interest/${articleId}"
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
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun block(
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Relationship/Block"
    ): Result<Unit>

    /**
     * 取消封鎖
     *
     * @param memberId 取消封鎖目標Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun unblock(
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Relationship/Unblock"
    ): Result<Unit>

    /**
     * 取得指定使用者封鎖的對象清單
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBlockingList(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Relationship/GetBlockingList"
    ): Result<List<Long>>

    /**
     * 取得指定使用者被對象封鎖的清單
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBlockers(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Relationship/GetBlockers"
    ): Result<List<Long>>

    /**
     * 取得指定的會員清單與我目前的關係
     *
     * @param memberIdList 會員清單
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRelationshipWithMe(
        memberIdList: List<Long>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Relationship/GetRelationshipWithMe"
    ): Result<List<RelationshipWithMe>>

    /**
     * 使用者檢舉文章 (主文回文Id帶null 回文帶回文Id)
     *
     * @param articleId 文章ID
     * @param reasonType 檢舉原因 [對照表(Report) -> http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html]
     * @param commentId 回文Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    @Deprecated("檢舉留言請使用createReportV2，檢舉主文仍暫時使用這個，待服務實作後遷移")
    suspend fun createReport(
        articleId: Long,
        reasonType: Int,
        commentId: Long?,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Report/Create/${articleId}"
    ): Result<Unit>

    /**
     * 使用者檢舉文章/留言
     *
     * @param id 文章或留言ID(目前僅支援留言)
     * @param reasonType 檢舉原因 [對照表(Report) -> http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html]
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createReportV2(
        id: String,
        reasonType: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${id}/Report"
    ): Result<Unit>

    /**
     * 刪除指定使用者檢舉文章的記錄
     *
     * @param articleId 文章ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun deleteReport(
        articleId: Long,
        commentId: Long?,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Report/Delete/${articleId}"
    ): Result<Unit>

    /**
     * 取得頻道ID對應的會員ID
     *
     * @param channelIdList 頻道ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberIds(
        channelIdList: List<Long>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Support/GetMemberIds"
    ): Result<List<ChannelIdAndMemberId>>

    /**
     * 取得會員ID對應的頻道ID
     *
     * @param memberIdList 會員ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getChannelIds(
        memberIdList: List<Long>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Support/GetChannelIds"
    ): Result<List<ChannelIdAndMemberId>>

    /**
     * 以關鍵字搜尋會員
     *
     * @param keyword 關鍵字
     * @param offset 偏移數量
     * @param fetch 查詢數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchMembers(
        keyword: String,
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Support/SearchMember"
    ): Result<List<SearchMembersResponseBody>>

    /**
     * 新增投票
     *
     * @param articleId 文章Id
     * @param optionIndex 選項編號(從0開始)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createVote(
        articleId: Long,
        optionIndex: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Vote/Create/${articleId}"
    ): Result<Unit>

    /**
     * 取得投票
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getCurrentVote(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Vote/GetCurrentVotes/${articleId}"
    ): Result<List<VoteInfo>>

    /**
     * 取得個股排行(不含美股)
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getCommodityRank(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/Commodity"
    ): Result<List<GetCommodityRankResponseBody>>

    /**
     * 取得美股排行
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getUSCommodityRank(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/USCommodity"
    ): Result<List<GetCommodityRankResponseBody>>

    /**
     * 取得達人排行
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getExpertMemberRank(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/ExpertMember"
    ): Result<List<GetExpertMemberRankResponseBody>>

    /**
     * 取得指定達人排行
     *
     * @param memberIds(用逗號分隔)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSpecificExpertMemberRank(
        memberIds: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/SpecificExpertMemberRanks"
    ): Result<List<GetExpertMemberRankResponseBody>>

    /**
     * 取得粉絲成長達人排行
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberFansRank(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/MemberFansRank"
    ): Result<List<FansMemberRankResponseBody>>

    /**
     * 取得指定粉絲成長達人排行
     *
     * @param memberIds(用逗號分隔)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSpecificMemberFansRank(
        memberIds: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/SpecificMemberFansRank"
    ): Result<List<FansMemberRankResponseBody>>

    /**
     * 取得解題達人排行
     *
     * @param offset 跳過的數量
     * @param fetch 拿取數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSolutionExpertRank(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/SolutionExpert"
    ): Result<List<SolutionExpertRankResponseBody>>

    /**
     * 取得指定解題達人排行
     *
     * @param memberIds(用逗號分隔)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSpecificSolutionExpertRank(
        memberIds: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rank/SpecificSolutionExpert"
    ): Result<List<SolutionExpertRankResponseBody>>

    /**
     * 取得使用者通知
     *
     * @param updateTime  指定通知的時間(不包含) (unixTime 單位:毫秒)
     * @param offsetCount 取得通知筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getNotify(
        updateTime: Long,
        offsetCount: Int = 5,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Notify/Get"
    ): Result<List<GetNotifyResponseBody>>

    /**
     * 取得使用者通知數量
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getNotifyCount(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Notify/GetCount"
    ): Result<GetNotifyCountResponseBody>

    /**
     * 重設使用者通知數量
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun resetNotifyCount(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Notify/ResetCount"
    ): Result<Unit>

    /**
     * 設定通知為已讀
     *
     * @param notifyType 通知類型
     * @param mergeKey 合併主鍵
     * @param isNew 是否為新通知
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun setNotifyRead(
        notifyType: String,
        mergeKey: String,
        isNew: Boolean,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Notify/SetRead"
    ): Result<Unit>

    /**
     * P 幣兌換專欄文章
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun exchangeColumnArticle(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/BonusPointExchange/${articleId}"
    ): Result<Unit>

    /**
     * 取得會員的社群角色
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRole(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Role"
    ): Result<Set<Role>>


    /**
     * 依角色類型取得會員名單
     *
     * @param roleId 1: KOL
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMembersByRole(
        roleId: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Role/Id/${roleId}"
    ): Result<List<Long>>

    /**
     * 取得其他使用者的角色
     *
     * @param memberId 用戶Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRoleByMemberId(
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Role/${memberId}"
    ): Result<Set<Role>>

    /**
     * 使用者已兌換該作者文章數及上限
     *
     * @param memberId 用戶Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getExchangeCount(
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/ExchangeCount/${memberId}"
    ): Result<ExchangeCount>

    /**
     * 取得用戶是否訂閱該專欄作家
     *
     * @param memberId 用戶Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun isMemberSubscribe(
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/IsMemberSubscribe/${memberId}"
    ): Result<Boolean>

    /**
     * 取得專欄作家Vip社團資訊
     *
     * @param columnistMemberId 專欄作家Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getColumnistVipGroup(
        columnistMemberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/ColumnistVipGroup/$columnistMemberId"
    ): Result<GetColumnistVipGroupResponse>

    /**
     * 取得指定研究報告文章ID
     *
     * @param date 日期(yyyyMMdd)
     * @param brokerId 券商代號
     * @param stockId 股票代號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockReportId(
        date: String,
        brokerId: String,
        stockId: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/StockReport"
    ): Result<Int>

    /**
     * 取得指定社團資訊
     *
     * @param groupId 社團Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupV2(
        groupId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/${groupId}"
    ): Result<Group>

    /**
     * 依角色取得會員所有社團(MemberId不帶等於取自己)
     *
     * @param memberId 會員Id
     * @param roles 角色
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupByRoles(
        memberId: Long?,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/All"
    ): Result<List<Group>>

    /**
     * 創建社團
     *
     * @param group 社團資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createGroup(
        group: GroupManipulation,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group"
    ): Result<Long>

    /**
     * 更新社團資訊
     *
     * @param groupId 社團Id
     * @param group 社團資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateGroup(
        groupId: Long,
        group: GroupManipulation,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/${groupId}"
    ): Result<Unit>

    /**
     * 解散社團(鎖權限：社長)
     *
     * @param groupId 社團Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun dismissGroup(
        groupId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/${groupId}"
    ): Result<Unit>

    /**
     * 增加社團的板(鎖權限:社長)
     *
     * @param groupId 社團Id
     * @param isChatRoom 是否為聊天室
     * @param board 看板資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createGroupBoard(
        groupId: Long,
        isChatRoom: Boolean,
        board: BoardManipulation,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/${groupId}/Board"
    ): Result<Long>

    /**
     * 修改看板
     *
     * @param boardId 看板Id
     * @param board 看板資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateGroupBoard(
        boardId: Long,
        board: BoardManipulation,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Board/${boardId}"
    ): Result<Unit>

    /**
     * 取得社團所有看板
     *
     * @param groupId 社團Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupBoards(
        groupId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/${groupId}/Board/All"
    ): Result<List<Board>>

    /**
     * 取得特定看板資訊
     *
     * @param boardId 看板Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupBoard(
        boardId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Board/${boardId}"
    ): Result<BoardSingle>

    /**
     * 刪除社團看板(鎖權限：幹部以上)
     *
     * @param boardId 看板Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun deleteGroupBoard(
        boardId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Board/${boardId}"
    ): Result<Unit>

    /**
     * 取得社團是否有未察看的待審用戶
     *
     * @param groupId 社團Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun hasNewGroupPending(
        groupId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/${groupId}/HasNewPending"
    ): Result<Boolean>

    /**
     * 取得該社員在社團的所有角色
     *
     * @param groupId 社團Id
     * @param memberId 會員Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupMemberRoles(
        groupId: Long,
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/${memberId}/Role"
    ): Result<MemberRoles>

    /**
     * 設定社團身份(鎖權限：幹部以上)
     *
     * @param groupId 社團Id
     * @param memberId 會員Id
     * @param roleIds 角色Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateGroupMemberRoles(
        groupId: Long,
        memberId: Long,
        roleIds: List<Int>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/${memberId}/Role"
    ): Result<Unit>

    /**
     * 取得社團成員列表
     *
     * @param groupId 社團Id
     * @param roles 角色
     * @param offset 起始位置
     * @param fetch 取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupMembers(
        groupId: Long,
        roles: List<com.cmoney.backend2.forumocean.service.api.group.v2.Role>,
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}"
    ): Result<List<GroupMember2>>

    /**
     * 離開社團
     *
     * @param groupId 社團Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun leaveGroup(
        groupId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}"
    ): Result<Unit>

    /**
     * 取得社長及幹部清單
     *
     * @param groupId 社團Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupAdmins(
        groupId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/Admins"
    ): Result<Admins>

    /**
     * 搜尋社員
     *
     * @param groupId 社團Id
     * @param keyword 關鍵字
     * @param offset 起始位置
     * @param fetch 取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchGroupMember(
        groupId: Long,
        keyword: String,
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/Search"
    ): Result<List<com.cmoney.backend2.forumocean.service.api.group.v2.GroupMember>>

    /**
     * 申請加入社團
     *
     * @param groupId 社團Id
     * @param joinGroupRequest 申請加入社團的資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun joinGroup(
        groupId: Long,
        joinGroupRequest: JoinGroupRequest,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/Join"
    ): Result<Unit>

    /**
     * 取得審核成員列表(鎖權限：幹部以上)
     *
     * @param groupId 社團Id
     * @param timestamp 基準時間戳(帶入可取得此時間戳之前的資料)
     * @param fetch 取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupPendingRequests(
        groupId: Long,
        timestamp: Long,
        fetch: Int = 20,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/Pending"
    ): Result<PendingRequests>

    /**
     * 搜尋審核中的社員(鎖權限：幹部以上)
     *
     * @param groupId 社團Id
     * @param keyword 關鍵字
     * @param timestamp 基準時間戳(帶入可取得此時間戳之前的資料)
     * @param fetch 取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchGroupPendingRequests(
        groupId: Long,
        keyword: String,
        timestamp: Long,
        fetch: Int = 20,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/SearchPending"
    ): Result<PendingRequests>

    /**
     * 審核成員加入(鎖權限：幹部以上)
     *
     * @param groupId 社團Id
     * @param approval 審核資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun approvalGroupRequest(
        groupId: Long,
        approval: List<Approval>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/Approve"
    ): Result<Unit>

    /**
     * 踢出成員(鎖權限：幹部以上)
     *
     * @param groupId 社團Id
     * @param memberId 會員Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun kickGroupMember(
        groupId: Long,
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupMember/${groupId}/${memberId}"
    ): Result<Unit>

    /**
     * 對社團看板發文
     *
     * @param boardId 看板Id
     * @param content 文章內容
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createGroupArticle(
        boardId: Long,
        content: Content.Article.General,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupArticle/Board/${boardId}/normal"
    ): Result<CreateArticleResponseBody>

    /**
     * 取得指定看板文章
     * 取回來的文章中不包含起始權重那篇文章，不帶起始權重其預設值會根據fetch的正負而不同。 分以下情況:
     * fetch > 0時，起始權重會被設為long的最大值，並往權重小的文章取。
     * fetch < 0時，起始權重會被設為long的最小值，並往權重大的文章取。
     *
     * @param boardId 看板Id
     * @param startWeight 起始權重，可不帶，其預設值隨fetch的正負改變
     * @param fetch 欲取的文章篇數，不帶的話預設為10
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBoardArticles(
        boardId: Long,
        startWeight: Long?,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupArticle/Board/${boardId}"
    ): Result<GetGroupBoardArticlesResponse>

    /**
     * 聊天室: 收回自己的訊息
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun unsendArticle(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupArticle/${articleId}/Unsend"
    ): Result<Unit>

    /**
     * 取得用戶可以進入的所有看板 id
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAvailableBoardIds(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Board/All"
    ): Result<AvailableBoardIds>

    /**
     * 取得社團推播
     *
     * @param boardId 看板Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupBoardPushSetting(
        boardId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupNotification/Board/${boardId}/PushSetting"
    ): Result<PushType>

    /**
     * 設定社團推播
     *
     * @param boardId 看板Id
     * @param pushType 推播類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun setGroupBoardPushSetting(
        boardId: Long,
        pushType: PushType,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupNotification/Board/${boardId}/PushSetting"
    ): Result<Unit>

    /**
     * 取得會員的被評價資訊統計
     *
     * @param memberId 被評價會員
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberRatingCounter(
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rating/${memberId}"
    ): Result<MemberRatingCounter>

    /**
     * 取得指定評價
     *
     * @param creatorId 評價會員
     * @param memberId 被評價會員
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRatingComment(
        creatorId: Long,
        memberId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rating/Review/${creatorId}/${memberId}"
    ): Result<RatingComment>

    /**
     * 取得指定會員的被評價清單
     *
     * @param memberId 被評價會員ID
     * @param offset 跳過筆數
     * @param fetch 取得筆數
     * @param sortType 1: 新至舊; 2: 按評分遞減; 3: 按評分遞增
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberRatingComments(
        memberId: Long,
        offset: Int,
        fetch: Int,
        sortType: SortType,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rating/Reviews/${memberId}"
    ): Result<List<OthersRatingComment>>

    /**
     * 滿分為5, 評論字數不可多於200
     *
     * @param request 評論內容
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun reviewUser(
        request: ReviewRequest,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Rating/Review"
    ): Result<String>


    /**
     * 取得專欄作家清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getColumnistAll(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Columnist/All"
    ): Result<List<Long>>

    /**
     * 取得文章(不確定文章類型)v2
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getArticleV2(
        articleId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${articleId}"
    ): Result<ArticleResponseBodyV2>

    /**
     * 取得指定主文或回文的回文清單V2
     *
     * @param articleId 指定主文或回文Id
     * @param startCommentIndex 起始回文index
     * @param fetch 取得回文數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getCommentV2(
        articleId: String,
        startCommentIndex: Long?,
        fetch: Int?,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${articleId}/Comments"
    ): Result<GetCommentsResponseBody>

    /**
     * 取得指定 index 的留言
     *
     * @param id 主文或回文Id
     * @param commentIndices 回文index清單
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getCommentsByIndex(
        id: String,
        commentIndices: List<Long>,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/CommentsByIndex"
    ): Result<List<CommentResponseBodyV2>>

    /**
     * 對指定主文或留言發一篇回文
     *
     * @param id 主文或留言id
     * @param text content
     * @param multiMedia ContentType
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createCommentV2(
        id: String,
        text: String?,
        multiMedia: List<MediaType>?,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${id}/Comment"
    ): Result<CreateCommentResponseBodyV2>

    /**
     * 隱藏/取消隱藏留言
     *
     * @param id 留言Id
     * @param isHide 是否隱藏
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun changeCommentHideState(
        id: String,
        isHide: Boolean,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${id}/Hide"
    ): Result<Unit>

    /**
     * 取得單一留言
     *
     * @param commentId 留言Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSingleComment(
        commentId: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Article/${commentId}"
    ): Result<CommentResponseBodyV2>


    /**
     * 取得推薦文章
     *
     * @param offset 起始權重
     * @param fetch 取得數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRecommendation(
        offset: Int,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Channel/GetRecommendation/Recommendation"
    ): Result<GetRecommendationResponse>

    /**
     * 取得置頂精選文章清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPinPromotedArticles(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/PromotedArticle/Pin"
    ): Result<List<PromotedArticleResponseBody>>

    /**
     * 取得精選文章清單
     *
     * @param startWeight 起始權重
     * @param fetch 取得的數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPromotedArticles(
        startWeight: Long,
        fetch: Int,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/PromotedArticle"
    ): Result<GetPromotedArticlesResponse>

    /**
     * 取得聊天室列表
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAllChatRoom(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Chatroom/All"
    ): Result<List<GetAllChatRoomResponse>>

    /**
     * 取得使用者未檢查的聊天室看板數
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getUncheckChatRoomCount(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Chatroom/UncheckChatroomCount"
    ): Result<GetUncheckChatRoomCountResponse>

    /**
     * 重設使用者未檢查的聊天室看板數
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun resetUncheckChatRoomCount(
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/Group/Chatroom/UncheckChatroomCount"
    ): Result<Unit>

    /**
     * 釘選社團看板文章
     *
     * @param articleId 文章編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun pinSpaceBoardArticle(
        articleId: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupArticle/${articleId}/Pin"
    ): Result<Unit>

    /**
     * 取消釘選社團看板文章
     *
     * @param articleId 文章編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun unpinSpaceBoardArticle(
        articleId: String,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupArticle/${articleId}/Unpin"
    ): Result<Unit>

    /**
     * 取得社團看板置頂文章列表
     *
     * @param boardId 看板 ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSpaceBoardPinArticles(
        boardId: Long,
        domain: String = manager.getForumOceanSettingAdapter().getDomain(),
        url: String = "${domain}${manager.getForumOceanSettingAdapter().getPathName()}api/GroupArticle/Board/${boardId}/Pin"
    ): Result<GetSpaceBoardPinArticlesResponseBody>
}