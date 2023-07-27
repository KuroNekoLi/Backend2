package com.cmoney.backend2.ocean.service

import androidx.annotation.IntRange
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBody
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBody
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
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBody
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelResponseBody
import com.cmoney.backend2.ocean.service.api.setreaded.NotifyIdAndIsSpecificPair
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionResponseBody
import com.cmoney.backend2.ocean.service.api.uploadchannelimage.UploadChannelImageResponseBody
import com.cmoney.backend2.ocean.service.api.variable.AnswerParam
import com.cmoney.backend2.ocean.service.api.variable.ArticleNeedInfo
import com.cmoney.backend2.ocean.service.api.variable.ChannelInfoOption
import com.cmoney.backend2.ocean.service.api.variable.ChannelNeedInfo
import com.cmoney.backend2.ocean.service.api.variable.ChannelTypes
import com.cmoney.backend2.ocean.service.api.variable.FilterType
import com.cmoney.backend2.ocean.service.api.variable.MemberPosition
import com.cmoney.backend2.ocean.service.api.variable.NotificationType
import com.cmoney.backend2.ocean.service.api.variable.Relation
import com.cmoney.backend2.ocean.service.api.variable.SuccessResult
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import java.io.File

interface OceanWeb {

    val manager: GlobalBackend2Manager

    /**
     * 全穿或脫掉所有徽章
     *
     * @param isWear 已裝備徽章
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun changeAllBadge(
        isWear: Boolean,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/ChangeAllBadge"
    ): Result<SuccessResult>

    /**
     * 更換裝備的徽章(目前單選由前端控制)
     *
     * @param isWear 已裝備徽章
     * @param badgeIds 徽章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun changeWearBadge(
        isWear: Boolean,
        badgeIds: List<Int>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/ChangeWearBadge"
    ): Result<SuccessResult>

    /**
     * 取得所有達成徽章及達成條件
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBadgeAndRequirement(
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/GetBadgeAndRequirement"
    ): Result<List<GetBadgeAndRequirementResponse>>

    /**
     * 取得所有徽章持有狀態
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBadgesCollection(
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/GetBadgesCollection"
    ): Result<List<GetBadgesCollection>>

    /**
     * 取得目前所有指標清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMetricsStats(
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/GetMetricsStats"
    ): Result<List<GetMetricsStats>>

    /**
     * 取得未讀徽章
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getUnreadBadges(
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/GetUnreadBadges"
    ): Result<List<Int>>

    /**
     * 取得頻道裝備的徽章(多個頻道)
     *
     * @param channelIds 頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun channelWearBadge(
        channelIds: List<Long>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/ChannelWearBadge"
    ): Result<List<ChannelWearBadge>>

    /**
     * 標記徽章已讀
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun setBadgeRead(
        badgeId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Badge/SetBadgeRead"
    ): Result<SuccessResult>

    /**
     * 取得社團問卷
     *
     * @param channelId 頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getChannelQuestions(
        channelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Questionnaire/GetChannelQuestions"
    ): Result<GetChannelQuestionsResponse>

    /**
     * 新增/更新社團問卷
     *
     * @param questionnaire 社團問卷
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun channelQuestions(
        questionnaire: ChannelQuestionnaire,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Questionnaire/ChannelQuestions"
    ): Result<SuccessResult>

    /**
     * 開啟或關閉加入社團問卷
     *
     * @param isActive 啟用狀態
     * @param channelId 頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun channelQuestionsActivation(
        isActive: Boolean,
        channelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Questionnaire/ChannelQuestionsActivation"
    ): Result<ChannelQuestionsActivationResponse>

    /**
     * 取得問卷回答
     *
     * @param channelId 頻道Id
     * @param memberChannelIds 會員頻道Id
     * @param questionIds 問卷Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAnswers(
        channelId: Long,
        memberChannelIds: List<Long>,
        questionIds: List<Long>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Questionnaire/GetAnswers"
    ): Result<GetAnswersResponse>

    /**
     * 新增問卷回答
     *
     * @param answers 問卷回答參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun answers(
        answers: List<AnswerParam>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Questionnaire/Answers"
    ): Result<SuccessResult>

    /**
     * 查詢多用戶是否有手機權限
     *
     * @param channelId 頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun hadPhoneAuthentication(
        channelId: List<Long>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/MemberBadge/HadPhoneAuthentication"
    ): Result<HadPhoneAuthResponse>

    /**
     * 取得股票最新文章清單
     *
     * @param baseArticleId 由哪個ArticleId開始往前拿資料，第一次填0
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章
     * @param stockIdList 股票代號(不可超過100檔股票代號
     * @param articleNeedInfo
     * @param filterType 篩選文章性質類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockLatestArticle(
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetStockLatestArticle"
    ): Result<GetStockLatestArticleResponse>

    /**
     * 取得頻道最新清單
     *
     * @param baseArticleId 由哪個ArticleId開始往前拿資料，第一次填0
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章
     * @param channelIdList 頻道Id
     * @param articleNeedInfo 文章需要的資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getChannelLatestArticle(
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        channelIdList: List<Long>,
        articleNeedInfo: ArticleNeedInfo,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetChannelLatestArticle"
    ): Result<GetChannelLatestArticleResponse>

    /**
     * 設定收藏文章/取消收藏文章
     *
     * @param articleId 文章Id
     * @param isCollect 是否收藏 true為收藏 false為不收藏,
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun changeCollectArticleState(
        articleId: Long,
        isCollect: Boolean,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetArticle/ChangeCollectArticleState"
    ): Result<SuccessResult>

    /**
     * 檢舉文章
     *
     * @param articleId 被檢舉的文章Id
     * @param reason 檢舉原因 (最多50字)(檢舉原因有:垃圾廣告、人身攻擊、根本詐騙集團、與投資無關)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun impeachArticle(
        articleId: Long,
        reason: String,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetArticle/ImpeachArticle"
    ): Result<SuccessResult>

    /**
     * 刪除文章
     *
     * @param articleId 文章Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun deleteArticle(
        articleId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetArticle/DeleteArticle"
    ): Result<SuccessResult>

    /**
     * 取得股票最熱文章
     *
     * @param skipCount 要跳過的筆數
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章
     * @param stockIdList 股票代號(不可超過100檔股票代號)
     * @param articleNeedInfo 文章需要的資訊
     * @param filterType 篩選文章性質類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockPopularArticle(
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetStockPopularArticle"
    ): Result<GetStockPopularArticleResponse>

    /**
     * 加入我的黑名單成功or失敗(原本就存在黑名單內視為成功)
     *
     * @param blackChannelId 要加入黑名單的頻道Id
     *
     */
    suspend fun putOnBlackList(
        blackChannelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/ChannelBlackList/PutOnBlackList"
    ): Result<SuccessResult>

    /**
     * 從我的黑名單移除成功or失敗(原本就不存在黑名單內也視為成功)
     *
     * @param blackChannelId 要移除黑名單的頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun spinOffBlackList(
        blackChannelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/ChannelBlackList/SpinOffBlackList"
    ): Result<SuccessResult>

    /**
     * 取得我的黑名單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBlackList(
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/ChannelBlackList/GetBlackList"
    ): Result<GetBlackListResponseBody>

    /**
     * 取得黑名單我的人
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBlockList(
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/ChannelBlackList/GetBlockList"
    ): Result<GetBlockListResponseBody>

    /**
     * 取得通知
     *
     * @param isIncludeClub 是否包含社團
     * @param lowerBoundNotifyTime 通知時間下限
     * @param notifyTypes 通知類型
     * @param fetchCount 要取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getNotify(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        fetchCount: Int,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Notify/GetNotify"
    ): Result<GetNotifyResponseBody>

    /**
     * 取得未讀通知數
     *
     * @param isIncludeClub 是否包含社團相關通知
     * @param lowerBoundNotifyTime 早於此通知時間點的未讀通知
     * @param notifyTypes 顯示的通知類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getUnreadCount(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Notify/GetUnreadCount"
    ): Result<GetUnreadCountResponseBody>

    /**
     * 設定通知已讀
     *
     * @param notifyIdAndIsSpecificPair 通知Id與是否為特定通知
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun setReaded(
        notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Notify/SetReaded"
    ): Result<Unit>

    /**
     * 取得指定文章
     *
     * @param articleId 文章Id
     * @param articleNeedInfo 文章需要的資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSingleArticle(
        articleId: Long,
        articleNeedInfo: ArticleNeedInfo,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetSingleArticleV2"
    ): Result<GetSingleArticleResponseBody>

    /**
     * 取得指定文章的回覆
     *
     * @param articleId 文章Id
     * @param upperBoundArticleId 回覆文章Id上限
     * @param fetchCount 要取得的筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getComments(
        articleId: Long,
        upperBoundArticleId: Long,
        fetchCount: Int,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Comment/GetComments"
    ): Result<GetCommentsResponseBody>

    /**
     * 取得管理者清單 (幹部、社長)
     *
     * @param needInfo 需要額外的頻道資訊
     * @param channelId 查詢頻道編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getManagerList(
        needInfo: Int,
        channelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetClub/GetMangerList"
    ): Result<GetManagerList>

    /**
     * 取得管理者清單 (幹部、社長)
     *
     * @param clubChannelId 查詢社團頻道編號
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getManagerList(
        clubChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetClub/GetMangerList"
    ): Result<GetManagerList>

    /**
     * 是否有參加的社團
     *
     * @param channelId 頻道Id
     * @param relation 我加入的社團
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */

    suspend fun isJoinedClub(
        channelId: Long,
        relation: Relation,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetClub/CheckHasJoinedClub"
    ): Result<HasJoinedClubComplete>

    /**
     * 取得指定頻道資訊(其他類型頻道)
     *
     * @param channelId 頻道Id
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getOtherChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Other>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/GetChannelInfo"
    ): Result<ChannelInfo.ChannelBaseInfo>

    /**
     * 取得指定頻道資訊(會員頻道資訊)
     *
     * @param channelId 頻道Id
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/GetChannelInfo"
    ): Result<ChannelInfo.MemberChannelInfo>

    /**
     * 取得指定頻道資訊(社團頻道資訊)
     *
     * @param channelId 頻道Id
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getClubChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/GetChannelInfo"
    ): Result<ChannelInfo.ClubChannelInfo>

    /**
     * 取得指定頻道資訊(RSS、訊號頻道資訊)
     *
     * @param channelId 頻道Id
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRssSignalChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.RssSignal>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/GetChannelInfo"
    ): Result<ChannelInfo.RssSignalChannelInfo>

    /**
     * 搜尋社團 ( 搜尋頻道 )
     *
     * @param channelTypes 頻道類型
     * @param fetchCount 取得數量
     * @param keyword 查詢關鍵字
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchChannel(
        channelTypes: ChannelTypes,
        fetchCount: Int,
        keyword: String,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/SearchChannel"
    ): Result<SearchChannelResponseBody>

    /**
     * 取得排除加入社團的粉絲清單(已邀請或審核中或黑名單的粉絲也會排除)
     *
     * @param needInfo 需要額外的頻道資訊
     * @param excludeClubChannelId 排除的社團頻道編號
     * @param fetchCount 取得數量
     * @param skipCount 跳過數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getFanListExcludeJoinedClub(
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        excludeClubChannelId: Long,
        fetchCount: Int,
        skipCount: Int,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/GetFansListExcludeJoinedClub"
    ): Result<GetFansListExcludeJoinedClubResponseBody>

    /**
     * 取得頻道基本資訊(多筆)
     *
     * @param channelIds 頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getSimpleChannelInfo(
        channelIds: List<Long>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetChannel/GetSimpleChannelInfo"
    ): Result<GetSimpleChannelInfoResponseBody>

    /**
     * 設定評價
     *
     * @param channelId 頻道Id
     * @param content 評價內容
     * @param score 評價分數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun setEvaluation(
        channelId: Long,
        content: String,
        score: Int,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Evaluation/SetEvaluation"
    ): Result<SuccessResult>

    /**
     * 是否給過評價
     *
     * @param channelId 頻道Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun checkHasEvaluated(
        channelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Evaluation/CheckHasEvaluated"
    ): Result<CheckHasEvaluatedResponseBody>

    /**
     * 取得指定用戶的評價清單
     *
     * @param channelId 頻道Id
     * @param fetchCount 取得數量
     * @param skipCount 跳過數量
     * @param sortType 排序方式
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getEvaluationList(
        channelId: Long,
        fetchCount: Int,
        skipCount: Int,
        sortType: SortType,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Evaluation/GetEvaluationList"
    ): Result<GetEvaluationListResponseBody>

    /**
     * 取得收藏文章清單
     *
     * @param skipCount 略過幾筆
     * @param fetchCount 取得幾筆
     * @param articleNeedInfo 需要額外的文章相關資訊(二進位表示,參考通訊備註8-A)
     * @param isIncludeLimitedAskArticle 是否包未解答的問答文章 (預設:false)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getCollectArticleList(
        skipCount: Int,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        isIncludeLimitedAskArticle: Boolean,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetArticleList/GetCollectArticleList"
    ): Result<GetCollectArticleListResponseBody>

    /**
     * 取得大師排行榜
     *
     * @param masterType MasterType 解答大師排名(1-熱門值,2-粉絲增長,3-鑽石增長)
     * @param fetchCount Int 取得幾筆
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMasters(
        masterType: MasterType,
        fetchCount: Int,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Customization/GetMasters"
    ): Result<GetMastersResponseBody>

    /**
     * 取得問答最新文章
     *
     * @param baseArticleId 從哪篇文章Id開取拿文章
     * @param fetchCount 拿取篇數
     * @param stockListList 篩選需要哪些股票問答的清單
     * @param articleNeedInfo 需要那些文章資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAskLatestArticle(
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        stockListList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetAskLatestArticle"
    ): Result<GetAskLatestArticleResponseBody>

    /**
     * 取得多股大師評價分數
     *
     * @param stockIdList 股票Id清單
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockMasterEvaluationList(
        stockIdList: List<String>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Customization/GetStockMasterEvaluationList"
    ): Result<GetStockMasterEvaluationListResponseBody>

    /**
     * 取得個股大師評價
     *
     * @param stockId 股票Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockMasterEvaluation(
        stockId: String,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Customization/GetStockMasterEvaluation"
    ): Result<GetStockMasterEvaluationResponseBody>

    /**
     * 上傳社團頭像
     *
     * @param channelId 欲變更的頻道編號
     * @param image 欲變更的頭像
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun uploadChannelImage(
        channelId: Long,
        image: File? = null,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/File/UploadChannelImage"
    ): Result<UploadChannelImageResponseBody>

    /**
     * 創建社團
     *
     * @param clubName 社團名稱(12字以內)
     * @param description 社團介紹
     * @param joinMethod 加入方式 1:公開社團(自由參加，不需審核) 4:非公開社團(需審核)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createClub(
        clubName: String,
        description: String,
        joinMethod: JoinMethod,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/CreateClub"
    ): Result<CreateClubResponseBody>

    /**
     * 關閉社團
     *
     * @param clubChannelId 社團頻道編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun deleteClub(
        clubChannelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/DeleteClub"
    ): Result<DeleteClubResponseBody>

    /**
     * 離開社團
     *
     * @param clubChannelId 社團頻道編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun leaveClub(
        clubChannelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/LeaveClub"
    ): Result<LeaveClubResponseBody>

    /**
     * 邀請加入社團
     *
     * @param clubChannelId 社團頻道編號
     * @param memberChannelIds 邀請加入的會員頻道編號清單(以 , 分隔)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun invite(
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/Invitation"
    ): Result<InviteResponseBody>

    /**
     * 加入社團
     *
     * @param clubChannelId 社團頻道編號
     * @param message 申請訊息(審核用，可不填) (40字以內),
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun joinClub(
        clubChannelId: Long,
        message: String,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/JoinClub"
    ): Result<JoinClubResponseBody>

    /**
     * 取得指定會員的社團清單
     *
     * @param memberChannelId 查詢會員頻道編號
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberClubs(
        memberChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        relation: Relation,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetClub/GetMemberClubs"
    ): Result<GetMemberClubsResponseBody>

    /**
     * 取得推薦社團
     *
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRecommendClubs(
        fetchCount: Int,
        skipCount: Int,
        needInfo: RecommendClubsNeedInfo,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetClub/GetRecommendClubs"
    ): Result<GetRecommendClubsResponseBody>

    /**
     * 改變會員身分
     *
     * @param clubChannelId 社團頻道編號
     * @param memberChannelIds 以 , 分隔的會員頻道清單
     * @param operation 執行操作
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun changeMemberStatus(
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        operation: Operation,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/ChangeMemberStatus"
    ): Result<ChangeMemberStatusResponseBody>

    /**
     * 改變會員身分
     *
     * @param clubChannelId 社團頻道編號
     * @param description 社團介紹 (140字以內)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateClubDescription(
        clubChannelId: Long,
        description: String,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/SetClub/UpdateClubDescription"
    ): Result<UpdateClubDescriptionResponseBody>

    /**
     * 取得指定身份的社團成員清單(審核清單,黑名單)
     *
     * @param clubChannelId 查詢社團頻道編號
     * @param memberPosition 會員在社團的狀態
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param needInfo 需要額外的頻道資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberStatusList(
        clubChannelId: Long,
        memberPosition: MemberPosition,
        fetchCount: Int,
        skipCount: Int,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/GetClub/GetMemberStatusList"
    ): Result<GetMemberStatusListResponseBody>

    /**
     * 取得與某會員相關的重要留言(現為作者或管理者回覆)
     *
     * @param articleIds 文章編號
     * @param fetch 取得幾筆
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getRelevantComments(
        articleIds: List<Long>,
        fetch: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetRelevantComments"
    ): Result<GetRelevantCommentsResponse>

    /**
     * 取得主題標籤文章(可訪客)
     *
     * @param topic 主題標籤
     * @param baseArticleId 由哪個ArticleId開始往前拿資料
     * @param fetchCount 要取得筆數
     * @param articleNeedInfo 需要那些文章資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTopicArticles(
        topic: String,
        baseArticleId: Long,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetTopicArticles"
    ): Result<GetTopicArticlesResponseBody>

    /**
     * 取得個股下有主題標籤文章(可訪客)
     *
     * @param topic 主題標籤
     * @param baseArticleId 由哪個ArticleId開始往前拿資料
     * @param fetchCount 要取得筆數
     * @param articleNeedInfo 需要那些文章資訊
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockAndTopicArticles(
        stockId: String,
        topic: String,
        baseArticleId: Long,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/Article/GetStockAndTopicArticles"
    ): Result<GetStockAndTopicArticlesResponseBody>

    /**
     * 新增或更新公告(從已發出去的文章)
     *
     * @param clubChannelId 社團頻道編號
     * @param articleId 要設成公告的文章id
     * @param isPinned 是否置頂
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createOrUpdateAnnouncement(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/club/${clubChannelId}/createorupdateannouncement"
    ): Result<IsCreateOrUpdateSuccessResponse>

    /**
     * 拿到該社團全部公告
     *
     * @param clubChannelId 社團頻道編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAllAnnouncements(
        clubChannelId: Long,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/club/${clubChannelId}/readannouncements"
    ): Result<AnnouncementListResponse>

    /**
     * 刪除特定公告
     *
     * @param clubChannelId 社團編號
     * @param articleId 要刪除公告的文章id
     * @param isPinned 是否置頂
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun removeAnnouncements(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean,
        domain: String = manager.getOceanSettingAdapter().getDomain(),
        url: String = "${domain}OceanService/api/club/${clubChannelId}/removeannouncement"
    ): Result<IsRemoveAnnouncementSuccessResponse>

}