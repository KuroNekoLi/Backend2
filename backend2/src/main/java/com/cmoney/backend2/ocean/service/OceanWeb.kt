package com.cmoney.backend2.ocean.service

import androidx.annotation.IntRange
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusResponseBody
import com.cmoney.backend2.ocean.service.api.changememberstatus.Operation
import com.cmoney.backend2.ocean.service.api.channelquestions.ChannelQuestionnaire
import com.cmoney.backend2.ocean.service.api.channelquestionsactivation.ChannelQuestionsActivationResponse
import com.cmoney.backend2.ocean.service.api.channelwearbadge.ChannelWearBadge
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubComplete
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedResponseBody
import com.cmoney.backend2.ocean.service.api.club.AnnouncementListResponse
import com.cmoney.backend2.ocean.service.api.club.IsCreateOrUpdateSuccessResponse
import com.cmoney.backend2.ocean.service.api.club.IsRemoveAnnouncementSuccessResponse
import com.cmoney.backend2.ocean.service.api.createannouncement.CreateAnnouncementResponse
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBody
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBody
import com.cmoney.backend2.ocean.service.api.getAnnouncements.GetAnnouncementsResponse
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleListResponseBody
import com.cmoney.backend2.ocean.service.api.getanswers.GetAnswersResponse
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getbadgeandrequirement.GetBadgeAndRequirementResponse
import com.cmoney.backend2.ocean.service.api.getbadgescollection.GetBadgesCollection
import com.cmoney.backend2.ocean.service.api.getblacklist.GetBlackListResponseBody
import com.cmoney.backend2.ocean.service.api.getblocklist.GetBlockListResponseBody
import com.cmoney.backend2.ocean.service.api.getchannellatestarticle.GetChannelLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getchannelquestions.GetChannelQuestionsResponse
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubResponseBody
import com.cmoney.backend2.ocean.service.api.getmanagerlist.GetManagerList
import com.cmoney.backend2.ocean.service.api.getmasters.GetMastersResponseBody
import com.cmoney.backend2.ocean.service.api.getmasters.MasterType
import com.cmoney.backend2.ocean.service.api.getmemberclubs.GetMemberClubsResponseBody
import com.cmoney.backend2.ocean.service.api.getmemberstatuslist.GetMemberStatusListResponseBody
import com.cmoney.backend2.ocean.service.api.getmetricsstats.GetMetricsStats
import com.cmoney.backend2.ocean.service.api.getnotify.GetNotifyResponseBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.GetRecommendClubsResponseBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.RecommendClubsNeedInfo
import com.cmoney.backend2.ocean.service.api.getrelevantcomments.GetRelevantCommentsResponse
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoResponseBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesResponseBody
import com.cmoney.backend2.ocean.service.api.getstocklatestarticle.GetStockLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationResponseBody
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListResponseBody
import com.cmoney.backend2.ocean.service.api.getstockpopulararticle.GetStockPopularArticleResponse
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesResponseBody
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountResponseBody
import com.cmoney.backend2.ocean.service.api.hadphoneauthentication.HadPhoneAuthResponse
import com.cmoney.backend2.ocean.service.api.invite.InviteResponseBody
import com.cmoney.backend2.ocean.service.api.isinwhitelist.IsInCreateArticleWhiteListResponseBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBody
import com.cmoney.backend2.ocean.service.api.removeannouncement.RemoveAnnouncementResponse
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelResponseBody
import com.cmoney.backend2.ocean.service.api.setreaded.NotifyIdAndIsSpecificPair
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionResponseBody
import com.cmoney.backend2.ocean.service.api.uploadchannelimage.UploadChannelImageResponseBody
import com.cmoney.backend2.ocean.service.api.variable.*
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import java.io.File

interface OceanWeb {
    /**
     * 全穿或脫掉所有徽章
     *
     * @param apiParam
     * @param isWear
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun changeAllBadge(
        apiParam: MemberApiParam,
        isWear: Boolean
    ): Result<SuccessResult>

    /**
     * 全穿或脫掉所有徽章
     *
     * @param isWear
     * @return
     */
    suspend fun changeAllBadge(
        isWear: Boolean
    ): Result<SuccessResult>

    /**
     * 更換裝備的徽章(目前單選由前端控制)
     *
     * @param apiParam
     * @param isWear
     * @param badgeIds
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun changeWearBadge(
        apiParam: MemberApiParam,
        isWear: Boolean,
        badgeIds: List<Int>
    ): Result<SuccessResult>

    /**
     * 更換裝備的徽章(目前單選由前端控制)
     *
     * @param isWear
     * @param badgeIds
     * @return
     */
    suspend fun changeWearBadge(
        isWear: Boolean,
        badgeIds: List<Int>
    ): Result<SuccessResult>

    /**
     * 取得所有達成徽章及達成條件
     */
    suspend fun getBadgeAndRequirement(): Result<List<GetBadgeAndRequirementResponse>>

    /**
     * 取得所有徽章持有狀態
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getBadgesCollection(apiParam: MemberApiParam): Result<List<GetBadgesCollection>>

    /**
     * 取得所有徽章持有狀態
     */
    suspend fun getBadgesCollection(): Result<List<GetBadgesCollection>>


    /**
     * 取得目前所有指標清單
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getMetricsStats(apiParam: MemberApiParam): Result<List<GetMetricsStats>>

    /**
     * 取得目前所有指標清單
     */
    suspend fun getMetricsStats(): Result<List<GetMetricsStats>>

    /**
     * 取得未讀徽章
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getUnreadBadges(apiParam: MemberApiParam): Result<List<Int>>

    /**
     * 取得未讀徽章
     *
     * @param apiParam
     * @return
     */
    suspend fun getUnreadBadges(): Result<List<Int>>

    /**
     * 取得頻道裝備的徽章(多個頻道)
     *
     * @param apiParam
     * @param channelIds
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun channelWearBadge(
        apiParam: MemberApiParam,
        channelIds: List<Long>
    ): Result<List<ChannelWearBadge>>

    /**
     * 取得頻道裝備的徽章(多個頻道)
     *
     * @param channelIds
     */
    suspend fun channelWearBadge(channelIds: List<Long>): Result<List<ChannelWearBadge>>

    /**
     * 標記徽章已讀
     *
     * @param apiParam
     * @param badgeId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun setBadgeRead(apiParam: MemberApiParam, badgeId: Long): Result<SuccessResult>

    /**
     * 標記徽章已讀
     *
     * @param badgeId
     * @return
     */
    suspend fun setBadgeRead(badgeId: Long): Result<SuccessResult>

    /**
     * 取得社團問卷
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getChannelQuestions(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<GetChannelQuestionsResponse>

    /**
     * 取得社團問卷
     *
     * @param channelId
     * @return
     */
    suspend fun getChannelQuestions(channelId: Long): Result<GetChannelQuestionsResponse>

    /**
     * 新增/更新社團問卷
     *
     * @param apiParam
     * @param questionnaire
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun channelQuestions(
        apiParam: MemberApiParam,
        questionnaire: ChannelQuestionnaire
    ): Result<SuccessResult>

    /**
     * 新增/更新社團問卷
     *
     * @param questionnaire
     */
    suspend fun channelQuestions(
        questionnaire: ChannelQuestionnaire
    ): Result<SuccessResult>


    /**
     * 開啟或關閉加入社團問卷
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun channelQuestionsActivation(
        apiParam: MemberApiParam,
        isActive: Boolean,
        channelId: Long
    ): Result<ChannelQuestionsActivationResponse>

    /**
     * 開啟或關閉加入社團問卷
     *
     * @param channelId
     * @return
     */
    suspend fun channelQuestionsActivation(
        isActive: Boolean,
        channelId: Long
    ): Result<ChannelQuestionsActivationResponse>

    /**
     * 取得問卷回答
     *
     * @param apiParam
     * @param channelId
     * @param memberChannelIds
     * @param questionIds
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAnswers(
        apiParam: MemberApiParam,
        channelId: Long,
        memberChannelIds: List<Long>,
        questionIds: List<Long>
    ): Result<GetAnswersResponse>

    /**
     * 取得問卷回答
     *
     * @param channelId
     * @param memberChannelIds
     * @param questionIds
     * @return
     */
    suspend fun getAnswers(
        channelId: Long,
        memberChannelIds: List<Long>,
        questionIds: List<Long>
    ): Result<GetAnswersResponse>

    /**
     * 新增問卷回答
     *
     * @param apiParam
     * @param answers
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun answers(
        apiParam: MemberApiParam,
        answers: List<AnswerParam>
    ): Result<SuccessResult>

    /**
     * 新增問卷回答
     *
     * @param answers
     * @return
     */
    suspend fun answers(answers: List<AnswerParam>): Result<SuccessResult>

    /**
     * 查詢多用戶是否有手機權限
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun hadPhoneAuthentication(
        apiParam: MemberApiParam,
        channelId: List<Long>
    ): Result<HadPhoneAuthResponse>

    /**
     * 查詢多用戶是否有手機權限
     *
     * @param channelId
     * @return
     */
    suspend fun hadPhoneAuthentication(channelId: List<Long>): Result<HadPhoneAuthResponse>

    /**
     * 取得股票最新文章清單
     *
     * @param apiParam
     * @param baseArticleId
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param stockIdList
     * @param articleNeedInfo
     * @param filterType
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getStockLatestArticle(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockLatestArticleResponse>

    /**
     * 取得股票最新文章清單
     *
     * @param baseArticleId
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param stockIdList
     * @param articleNeedInfo
     * @param filterType
     * @return
     */
    suspend fun getStockLatestArticle(
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockLatestArticleResponse>

    /**
     * 取得頻道最新清單
     *
     * @param apiParam
     * @param baseArticleId
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param channelIdList
     * @param articleNeedInfo
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getChannelLatestArticle(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        channelIdList: List<Long>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetChannelLatestArticleResponse>

    /**
     * 取得頻道最新清單
     *
     * @param baseArticleId
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param channelIdList
     * @param articleNeedInfo
     * @return
     */
    suspend fun getChannelLatestArticle(
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        channelIdList: List<Long>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetChannelLatestArticleResponse>

    /**
     * 檢舉文章
     *
     * @param apiParam
     * @param articleId 被檢舉的文章Id
     * @param reason 檢舉原因 (最多50字)(檢舉原因有:垃圾廣告、人身攻擊、根本詐騙集團、與投資無關)
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun impeachArticle(
        apiParam: MemberApiParam,
        articleId: Long,
        reason: String
    ): Result<SuccessResult>

    /**
     * 檢舉文章
     *
     * @param articleId 被檢舉的文章Id
     * @param reason 檢舉原因 (最多50字)(檢舉原因有:垃圾廣告、人身攻擊、根本詐騙集團、與投資無關)
     * @return
     */
    suspend fun impeachArticle(
        articleId: Long,
        reason: String
    ): Result<SuccessResult>

    /**
     * 取得股票最熱文章
     *
     * @param apiParam
     * @param skipCount
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param stockIdList
     * @param articleNeedInfo
     * @param filterType
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getStockPopularArticle(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockPopularArticleResponse>

    /**
     * 取得股票最熱文章
     *
     * @param skipCount
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param stockIdList
     * @param articleNeedInfo
     * @param filterType
     * @return
     */
    suspend fun getStockPopularArticle(
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockPopularArticleResponse>

    /**
     * 加入我的黑名單成功or失敗(原本就存在黑名單內視為成功)
     *
     * @param apiParam
     * @param blackChannelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun putOnBlackList(
        apiParam: MemberApiParam,
        blackChannelId: Long
    ): Result<SuccessResult>

    /**
     * 加入我的黑名單成功or失敗(原本就存在黑名單內視為成功)
     *
     * @param blackChannelId
     * @return
     */
    suspend fun putOnBlackList(blackChannelId: Long): Result<SuccessResult>

    /**
     * 從我的黑名單移除成功or失敗(原本就不存在黑名單內也視為成功)
     *
     * @param apiParam
     * @param blackChannelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun spinOffBlackList(
        apiParam: MemberApiParam,
        blackChannelId: Long
    ): Result<SuccessResult>

    /**
     * 從我的黑名單移除成功or失敗(原本就不存在黑名單內也視為成功)
     *
     * @param blackChannelId
     * @return
     */
    suspend fun spinOffBlackList(blackChannelId: Long): Result<SuccessResult>


    /**
     * 取得我的黑名單
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getBlackList(apiParam: MemberApiParam): Result<GetBlackListResponseBody>

    /**
     * 取得我的黑名單
     */
    suspend fun getBlackList(): Result<GetBlackListResponseBody>


    /**
     * 取得黑名單我的人
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getBlockList(
        apiParam: MemberApiParam
    ): Result<GetBlockListResponseBody>

    /**
     * 取得黑名單我的人
     */
    suspend fun getBlockList(): Result<GetBlockListResponseBody>

    /**
     * 取得通知
     *
     * @param apiParam
     * @param isIncludeClub
     * @param lowerBoundNotifyTime
     * @param notifyTypes
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getNotify(
        apiParam: MemberApiParam,
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        fetchCount: Int
    ): Result<GetNotifyResponseBody>

    /**
     * 取得通知
     *
     * @param isIncludeClub
     * @param lowerBoundNotifyTime
     * @param notifyTypes
     * @return
     */
    suspend fun getNotify(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        fetchCount: Int
    ): Result<GetNotifyResponseBody>

    /**
     * 取得未讀通知數
     *
     * @param apiParam
     * @param isIncludeClub
     * @param lowerBoundNotifyTime
     * @param notifyTypes
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getUnreadCount(
        apiParam: MemberApiParam,
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>
    ): Result<GetUnreadCountResponseBody>

    /**
     * 取得未讀通知數
     *
     * @param isIncludeClub
     * @param lowerBoundNotifyTime
     * @param notifyTypes
     * @return
     */
    suspend fun getUnreadCount(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>
    ): Result<GetUnreadCountResponseBody>

    /**
     * 設定通知已讀
     *
     * @param apiParam
     * @param notifyIdAndIsSpecificPair
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun setReaded(
        apiParam: MemberApiParam,
        notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>
    ): Result<Unit>

    /**
     * 設定通知已讀
     *
     * @param notifyIdAndIsSpecificPair
     * @return
     */
    suspend fun setReaded(
        notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>
    ): Result<Unit>

    /**
     * 取得指定文章
     *
     * @param apiParam
     * @param articleId
     * @param articleNeedInfo
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getSingleArticle(
        apiParam: MemberApiParam,
        articleId: Long,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetSingleArticleResponseBody>

    /**
     * 取得指定文章
     *
     * @param articleId
     * @param articleNeedInfo
     * @return
     */
    suspend fun getSingleArticle(
        articleId: Long,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetSingleArticleResponseBody>

    /**
     * 取得指定文章的回覆
     *
     * @param apiParam
     * @param articleId
     * @param upperBoundArticleId
     * @param fetchCount
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getComments(
        apiParam: MemberApiParam,
        articleId: Long,
        upperBoundArticleId: Long,
        fetchCount: Int
    ): Result<GetCommentsResponseBody>

    /**
     * 取得指定文章的回覆
     *
     * @param articleId
     * @param upperBoundArticleId
     * @param fetchCount
     * @return
     */
    suspend fun getComments(
        articleId: Long,
        upperBoundArticleId: Long,
        fetchCount: Int
    ): Result<GetCommentsResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getManagerList(
        apiParam: MemberApiParam,
        needInfo: Int,
        channelId: Long
    ): Result<GetManagerList>

    suspend fun getManagerList(
        needInfo: Int,
        channelId: Long
    ): Result<GetManagerList>

    @Deprecated("ApiParam no longer required")
    suspend fun deleteArticle(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<SuccessResult>

    suspend fun deleteArticle(articleId: Long): Result<SuccessResult>

    @Deprecated("ApiParam no longer required")
    suspend fun isJoinedClub(
        apiParam: MemberApiParam,
        channelId: Long,
        relation: Relation
    ): Result<HasJoinedClubComplete>

    suspend fun isJoinedClub(
        channelId: Long,
        relation: Relation
    ): Result<HasJoinedClubComplete>

    /**
     * 取得指定頻道資訊(其他類型頻道)
     *
     * @param apiParam
     * @param channelId
     * @param needInfo
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getOtherChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Other>
    ): Result<ChannelInfo.ChannelBaseInfo>

    /**
     * 取得指定頻道資訊(其他類型頻道)
     *
     * @param channelId
     * @param needInfo
     * @return
     */
    suspend fun getOtherChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Other>
    ): Result<ChannelInfo.ChannelBaseInfo>

    /**
     * 取得指定頻道資訊(RSS、訊號頻道資訊)
     *
     * @param apiParam
     * @param channelId
     * @param needInfo
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getRssSignalChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.RssSignal>
    ): Result<ChannelInfo.RssSignalChannelInfo>

    /**
     * 取得指定頻道資訊(RSS、訊號頻道資訊)
     *
     * @param channelId
     * @param needInfo
     * @return
     */
    suspend fun getRssSignalChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.RssSignal>
    ): Result<ChannelInfo.RssSignalChannelInfo>

    /**
     * 取得指定頻道資訊(會員頻道資訊)
     *
     * @param apiParam
     * @param channelId
     * @param needInfo
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getMemberChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<ChannelInfo.MemberChannelInfo>

    /**
     * 取得指定頻道資訊(會員頻道資訊)
     *
     * @param channelId
     * @param needInfo
     * @return
     */
    suspend fun getMemberChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<ChannelInfo.MemberChannelInfo>


    /**
     * 取得指定頻道資訊(社團頻道資訊)
     *
     * @param apiParam
     * @param channelId
     * @param needInfo
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getClubChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>
    ): Result<ChannelInfo.ClubChannelInfo>

    /**
     * 取得指定頻道資訊(社團頻道資訊)
     *
     * @param channelId
     * @param needInfo
     * @return
     */
    suspend fun getClubChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>
    ): Result<ChannelInfo.ClubChannelInfo>

    /**
     * 搜尋社團 ( 搜尋頻道 )
     *
     * @param apiParam
     * @param channelTypes
     * @param fetchCount
     * @param keyword
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun searchChannel(
        apiParam: MemberApiParam,
        channelTypes: ChannelTypes,
        fetchCount: Int,
        keyword: String
    ): Result<SearchChannelResponseBody>

    /**
     * 搜尋社團 ( 搜尋頻道 )
     *
     * @param channelTypes
     * @param fetchCount
     * @param keyword
     * @return
     */
    suspend fun searchChannel(
        channelTypes: ChannelTypes,
        fetchCount: Int,
        keyword: String
    ): Result<SearchChannelResponseBody>

    /**
     * 取得排除加入社團的粉絲清單(已邀請或審核中或黑名單的粉絲也會排除)
     *
     * @param apiParam
     * @param needInfo
     * @param excludeClubChannelId
     * @param fetchCount
     * @param skipCount
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getFanListExcludeJoinedClub(
        apiParam: MemberApiParam,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        excludeClubChannelId: Long,
        fetchCount: Int,
        skipCount: Int
    ): Result<GetFansListExcludeJoinedClubResponseBody>

    /**
     * 取得排除加入社團的粉絲清單(已邀請或審核中或黑名單的粉絲也會排除)
     *
     * @param needInfo
     * @param excludeClubChannelId
     * @param fetchCount
     * @param skipCount
     * @return
     */
    suspend fun getFanListExcludeJoinedClub(
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        excludeClubChannelId: Long,
        fetchCount: Int,
        skipCount: Int
    ): Result<GetFansListExcludeJoinedClubResponseBody>

    /**
     * 取得頻道基本資訊(多筆)
     *
     * @param apiParam
     * @param channelIds
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getSimpleChannelInfo(
        apiParam: MemberApiParam,
        channelIds: List<Long>
    ): Result<GetSimpleChannelInfoResponseBody>

    /**
     * 取得頻道基本資訊(多筆)
     *
     * @param channelIds
     * @return
     */
    suspend fun getSimpleChannelInfo(
        channelIds: List<Long>
    ): Result<GetSimpleChannelInfoResponseBody>


    /**
     * 設定評價
     *
     * @param apiParam
     * @param channelId
     * @param content
     * @param score
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun setEvaluation(
        apiParam: MemberApiParam,
        channelId: Long,
        content: String,
        score: Int
    ): Result<SuccessResult>

    /**
     * 設定評價
     *
     * @param channelId
     * @param content
     * @param score
     * @return
     */
    suspend fun setEvaluation(
        channelId: Long,
        content: String,
        score: Int
    ): Result<SuccessResult>

    /**
     * 是否給過評價
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun checkHasEvaluated(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<CheckHasEvaluatedResponseBody>

    /**
     * 是否給過評價
     *
     * @param channelId
     * @return
     */
    suspend fun checkHasEvaluated(
        channelId: Long
    ): Result<CheckHasEvaluatedResponseBody>

    /**
     * 取得指定用戶的評價清單
     *
     * @param apiParam
     * @param channelId
     * @param fetchCount
     * @param skipCount
     * @param sortType
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getEvaluationList(
        apiParam: MemberApiParam,
        channelId: Long,
        fetchCount: Int,
        skipCount: Int,
        sortType: SortType
    ): Result<GetEvaluationListResponseBody>

    /**
     * 取得指定用戶的評價清單
     *
     * @param channelId
     * @param fetchCount
     * @param skipCount
     * @param sortType
     * @return
     */
    suspend fun getEvaluationList(
        channelId: Long,
        fetchCount: Int,
        skipCount: Int,
        sortType: SortType
    ): Result<GetEvaluationListResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun changeCollectArticleState(
        apiParam: MemberApiParam,
        articleId: Long,
        isCollect: Boolean
    ): Result<SuccessResult>

    /**
     * 設定收藏文章/取消收藏文章
     *
     * @param articleId Long
     * @param isCollect Boolean 是否收藏 true為收藏 false為不收藏
     */
    suspend fun changeCollectArticleState(
        articleId: Long,
        isCollect: Boolean
    ): Result<SuccessResult>

    @Deprecated("ApiParam no longer required")
    suspend fun getCollectArticleList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetCollectArticleListResponseBody>

    /**
     * 取得收藏文章清單
     *
     * @param skipCount 略過幾筆
     * @param fetchCount 取得幾筆
     * @param articleNeedInfo 需要額外的文章相關資訊(二進位表示,參考通訊備註8-A)
     * @param isIncludeLimitedAskArticle 是否包未解答的問答文章 (預設:false)
     */
    suspend fun getCollectArticleList(
        skipCount: Int,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetCollectArticleListResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun isInCreateArticleWhiteList(
        apiParam: MemberApiParam
    ): Result<IsInCreateArticleWhiteListResponseBody>

    /**
     * 取得使用者是否在白名單中
     *
     */
    suspend fun isInCreateArticleWhiteList(): Result<IsInCreateArticleWhiteListResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getMasters(
        apiParam: MemberApiParam,
        masterType: MasterType,
        fetchCount: Int
    ): Result<GetMastersResponseBody>

    /**
     * 取得大師排行榜
     *
     * @param masterType MasterType 解答大師排名(1-熱門值,2-粉絲增長,3-鑽石增長)
     * @param fetchCount Int 取得幾筆
     */
    suspend fun getMasters(
        masterType: MasterType,
        fetchCount: Int
    ): Result<GetMastersResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getAskLatestArticle(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        stockListList: List<String>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetAskLatestArticleResponseBody>

    /**
     * 取得問答最新文章
     *
     * @param baseArticleId 從哪篇文章Id開取拿文章
     * @param fetchCount 拿取篇數
     * @param stockListList 篩選需要哪些股票問答的清單
     * @param articleNeedInfo 需要那些文章資訊
     */
    suspend fun getAskLatestArticle(
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        stockListList: List<String>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetAskLatestArticleResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getStockMasterEvaluationList(
        apiParam: MemberApiParam,
        stockIdList: List<String>
    ): Result<GetStockMasterEvaluationListResponseBody>

    /**
     * 取得多股大師評價分數
     * @param apiParam MemberApiParam
     * @param stockIdList
     */
    suspend fun getStockMasterEvaluationList(
        stockIdList: List<String>
    ): Result<GetStockMasterEvaluationListResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getStockMasterEvaluation(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<GetStockMasterEvaluationResponseBody>

    /**
     * 取得個股大師評價
     * @param apiParam MemberApiParam
     * @param stockId
     */
    suspend fun getStockMasterEvaluation(
        stockId: String
    ): Result<GetStockMasterEvaluationResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun uploadChannelImage(
        apiParam: MemberApiParam,
        channelId: Long,
        image: File? = null
    ): Result<UploadChannelImageResponseBody>

    /**
     * 上傳社團頭像
     * @param apiParam MemberApiParam
     * @param channelId 欲變更的頻道編號
     * @param image 欲變更的頭像
     */
    suspend fun uploadChannelImage(
        channelId: Long,
        image: File? = null
    ): Result<UploadChannelImageResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun createClub(
        apiParam: MemberApiParam,
        clubName: String,
        description: String,
        joinMethod: JoinMethod
    ): Result<CreateClubResponseBody>

    /**
     * 創建社團
     * @param apiParam MemberApiParam
     * @param clubName 社團名稱(12字以內)
     * @param description 社團介紹
     * @param joinMethod 加入方式 1:公開社團(自由參加，不需審核) 4:非公開社團(需審核)
     */
    suspend fun createClub(
        clubName: String,
        description: String,
        joinMethod: JoinMethod
    ): Result<CreateClubResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun deleteClub(
        apiParam: MemberApiParam,
        clubChannelId: Long
    ): Result<DeleteClubResponseBody>

    /**
     * 關閉社團
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     */
    suspend fun deleteClub(clubChannelId: Long): Result<DeleteClubResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun leaveClub(
        apiParam: MemberApiParam,
        clubChannelId: Long
    ): Result<LeaveClubResponseBody>

    /**
     * 離開社團
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     */
    suspend fun leaveClub(clubChannelId: Long): Result<LeaveClubResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun invite(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        memberChannelIds: List<Long>
    ): Result<InviteResponseBody>

    /**
     * 邀請加入社團
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param memberChannelIds 邀請加入的會員頻道編號清單(以 , 分隔)
     */
    suspend fun invite(
        clubChannelId: Long,
        memberChannelIds: List<Long>
    ): Result<InviteResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun joinClub(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        message: String
    ): Result<JoinClubResponseBody>

    /**
     * 加入社團
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param message 申請訊息(審核用，可不填) (40字以內)
     */
    suspend fun joinClub(
        clubChannelId: Long,
        message: String
    ): Result<JoinClubResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getMemberClubs(
        apiParam: MemberApiParam,
        memberChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        relation: Relation
    ): Result<GetMemberClubsResponseBody>

    /**
     * 取得指定會員的社團清單
     * @param apiParam MemberApiParam
     * @param memberChannelId 查詢會員頻道編號
     * @param needInfo 需要額外的頻道資訊
     */
    suspend fun getMemberClubs(
        memberChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        relation: Relation
    ): Result<GetMemberClubsResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getRecommendClubs(
        apiParam: MemberApiParam,
        fetchCount: Int,
        skipCount: Int,
        needInfo: RecommendClubsNeedInfo
    ): Result<GetRecommendClubsResponseBody>

    /**
     * 取得推薦社團
     * @param apiParam MemberApiParam
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param needInfo 需要額外的頻道資訊
     */
    suspend fun getRecommendClubs(
        fetchCount: Int,
        skipCount: Int,
        needInfo: RecommendClubsNeedInfo
    ): Result<GetRecommendClubsResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun changeMemberStatus(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        operation: Operation
    ): Result<ChangeMemberStatusResponseBody>

    /**
     * 改變會員身分
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param memberChannelIds 以 , 分隔的會員頻道清單
     * @param operation 執行操作
     */
    suspend fun changeMemberStatus(
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        operation: Operation
    ): Result<ChangeMemberStatusResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun updateClubDescription(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        description: String
    ): Result<UpdateClubDescriptionResponseBody>

    /**
     * 改變會員身分
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param description 社團介紹 (140字以內)
     */
    suspend fun updateClubDescription(
        clubChannelId: Long,
        description: String
    ): Result<UpdateClubDescriptionResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getMemberStatusList(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        memberPosition: MemberPosition,
        fetchCount: Int,
        skipCount: Int,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetMemberStatusListResponseBody>

    /**
     * 取得指定身份的社團成員清單(審核清單,黑名單)
     * @param apiParam MemberApiParam
     * @param clubChannelId 查詢社團頻道編號
     * @param memberPosition 會員在社團的狀態
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param needInfo 需要額外的頻道資訊
     */
    suspend fun getMemberStatusList(
        clubChannelId: Long,
        memberPosition: MemberPosition,
        fetchCount: Int,
        skipCount: Int,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetMemberStatusListResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getManagerList(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetManagerList>

    /**
     * 取得社團管理者清單
     * @param clubChannelId 查詢社團頻道編號
     * @param needInfo 需要額外的頻道資訊
     */
    suspend fun getManagerList(
        clubChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetManagerList>

    suspend fun getAnnouncements(channelId: Long): Result<GetAnnouncementsResponse>

    suspend fun removeAnnouncements(
        channelId: Long,
        isPinned: Boolean,
        articleId: Long
    ): Result<RemoveAnnouncementResponse>

    suspend fun createAnnouncement(
        channelId: Long,
        isPinned: Boolean,
        articleId: Long
    ): Result<CreateAnnouncementResponse>

    suspend fun getRelevantComments(
        articleIds: List<Long>,
        fetch: Long
    ): Result<GetRelevantCommentsResponse>

    /**
     * 取得主題標籤文章(可訪客)
     * @param topic 主題標籤
     * @param baseArticleId 由哪個ArticleId開始往前拿資料
     * @param fetchCount 要取得筆數
     * @param articleNeedInfo 需要那些文章資訊
     */
    suspend fun getTopicArticles(
        topic: String,
        baseArticleId: Long,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetTopicArticlesResponseBody>

    /**
     * 取得個股下有主題標籤文章(可訪客)
     * @param topic 主題標籤
     * @param baseArticleId 由哪個ArticleId開始往前拿資料
     * @param fetchCount 要取得筆數
     * @param articleNeedInfo 需要那些文章資訊
     */
    suspend fun getStockAndTopicArticles(
        stockId: String,
        topic: String,
        baseArticleId: Long,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetStockAndTopicArticlesResponseBody>

    /**
     * 新增或更新公告(從已發出去的文章)
     * @param apiParam MemberApiParam
     * @param clubChannelId Long 社團頻道編號
     * @param articleId Long 要設成公告的文章id
     * @param isPinned Boolean 是否置頂
     */
    suspend fun createOrUpdateAnnouncement(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean
    ): Result<IsCreateOrUpdateSuccessResponse>

    /**
     * 拿到該社團全部公告
     * @param apiParam MemberApiParam
     * @param clubChannelId Long
     */
    suspend fun getAllAnnouncements(
        clubChannelId: Long
    ): Result<AnnouncementListResponse>

    /**
     * 刪除特定公告
     * @param apiParam MemberApiParam
     * @param clubChannelId Long 社團編號
     * @param articleId Long 要刪除公告的article id
     * @param isPinned Boolean 是否置頂
     */
    suspend fun removeAnnouncements(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean
    ): Result<IsRemoveAnnouncementSuccessResponse>

}