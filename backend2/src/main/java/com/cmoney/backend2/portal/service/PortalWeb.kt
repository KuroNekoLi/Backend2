package com.cmoney.backend2.portal.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo.AskAllMemberLastForecastInfo
import com.cmoney.backend2.portal.service.api.askmemberforecaststatus.AskMemberForecastStatus
import com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo.AskMemberLastForecastInfo
import com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo.GetActivitiesBaseInfo
import com.cmoney.backend2.portal.service.api.getactivitynowinfo.GetActivityNowInfo
import com.cmoney.backend2.portal.service.api.getadditionalinfo.CmPortalAddition
import com.cmoney.backend2.portal.service.api.getmemberperformance.GetMemberPerformance
import com.cmoney.backend2.portal.service.api.getpersonactivityhistory.GetPersonActivityHistory
import com.cmoney.backend2.portal.service.api.getranking.GetRanking
import com.cmoney.backend2.portal.service.api.getsignals.CmPortalSignal
import com.cmoney.backend2.portal.service.api.gettarget.CmPortalTarget
import com.cmoney.backend2.portal.service.api.joinactivity.JoinActivity

interface PortalWeb {
    /**
     * 服務1. 取得選股監控對象股票清單
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getTarget(
        apiParam: MemberApiParam
    ): Result<CmPortalTarget>

    /**
     * 服務1. 取得選股監控對象股票清單
     */
    suspend fun getTarget(): Result<CmPortalTarget>

    /**
     * 服務2. 取得選股全部條件篩選狀態
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getSignals(
        apiParam: MemberApiParam
    ): Result<CmPortalSignal>


    /**
     * 服務2. 取得選股全部條件篩選狀態
     */
    suspend fun getSignals(): Result<CmPortalSignal>

    /**
     * 服務3. 取得顯示資訊欄位資料
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAdditionalInfo(
        apiParam: MemberApiParam,
        settingId: Int
    ): Result<CmPortalAddition>

    /**
     * 服務3. 取得顯示資訊欄位資料
     */
    suspend fun getAdditionalInfo(
        settingId: Int
    ): Result<CmPortalAddition>


    /**
     * 詢問App的所有猜多空活動基本資訊
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getActivitiesBaseInfo(
        apiParam: MemberApiParam
    ): Result<GetActivitiesBaseInfo>

    /**
     * 詢問App的所有猜多空活動基本資訊
     */
    suspend fun getActivitiesBaseInfo(): Result<GetActivitiesBaseInfo>

    /**
     * 詢問目前猜多空活動狀況
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getActivityNowInfo(
        apiParam: MemberApiParam,
        commKey: String
    ): Result<GetActivityNowInfo>

    /**
     * 詢問目前猜多空活動狀況
     */
    suspend fun getActivityNowInfo(
        commKey: String
    ): Result<GetActivityNowInfo>

    /**
     * 取得會員戰績
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getMemberPerformance(
        apiParam: MemberApiParam,
        commKey: String,
        queryGuid: String
    ): Result<GetMemberPerformance>

    /**
     * 取得會員戰績
     */
    suspend fun getMemberPerformance(
        commKey: String,
        queryGuid: String
    ): Result<GetMemberPerformance>

    /**
     * 取得排行榜資料
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getRanking(
        apiParam: MemberApiParam,
        commKey: String,
        fetchCount: Int,
        skipCount: Int
    ): Result<List<GetRanking>>

    /**
     * 取得排行榜資料
     */
    suspend fun getRanking(
        commKey: String,
        fetchCount: Int,
        skipCount: Int
    ): Result<List<GetRanking>>

    /**
     * 取得某人的活動歷史紀錄
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getPersonActivityHistory(
        apiParam: MemberApiParam,
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        queryGuid: String
    ): Result<List<GetPersonActivityHistory>>

    /**
     * 取得某人的活動歷史紀錄
     */
    suspend fun getPersonActivityHistory(
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        queryGuid: String
    ): Result<List<GetPersonActivityHistory>>

    /**
     * 詢問會員目前猜多空活動參與狀況(驗證身分)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun askMemberForecastStatus(
        apiParam: MemberApiParam,
        commKey: String
    ): Result<AskMemberForecastStatus>

    /**
     * 詢問會員目前猜多空活動參與狀況(驗證身分)
     */
    suspend fun askMemberForecastStatus(
        commKey: String
    ): Result<AskMemberForecastStatus>

    /**
     * 詢問會員上期猜多空活動參與狀況(驗證身分)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun askMemberLastForecastInfo(
        apiParam: MemberApiParam,
        commKey: String
    ): Result<AskMemberLastForecastInfo>

    /**
     * 詢問會員上期猜多空活動參與狀況(驗證身分)
     */
    suspend fun askMemberLastForecastInfo(
        commKey: String
    ): Result<AskMemberLastForecastInfo>

    /**
     * 參與活動(驗證身分)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun joinActivity(
        apiParam: MemberApiParam,
        commKey: String,
        forecastValue: ForecastValue
    ): Result<JoinActivity>

    /**
     * 參與活動(驗證身分)
     */
    suspend fun joinActivity(
        commKey: String,
        forecastValue: ForecastValue
    ): Result<JoinActivity>

    /**
     * 詢問會員某App上期全部的猜多空活動參與狀況(驗證身分)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun askAllMemberLastForecastInfo(
        apiParam: MemberApiParam
    ): Result<AskAllMemberLastForecastInfo>

    /**
     * 詢問會員某App上期全部的猜多空活動參與狀況(驗證身分)
     */
    suspend fun askAllMemberLastForecastInfo(): Result<AskAllMemberLastForecastInfo>
}