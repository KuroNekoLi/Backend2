package com.cmoney.backend2.portal.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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

    val manager: GlobalBackend2Manager

    /**
     * 服務1. 取得選股監控對象股票清單
     */
    suspend fun getTarget(
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}cmportal/api/pickstock/gettarget/"
    ): Result<CmPortalTarget>


    /**
     * 服務2. 取得選股全部條件篩選狀態
     */
    suspend fun getSignals(
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}cmportal/api/pickstock/getsignals/"
    ): Result<CmPortalSignal>

    /**
     * 服務3. 取得顯示資訊欄位資料
     */
    suspend fun getAdditionalInfo(
        settingId: Int,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}cmportal/api/additionalinformation/getadditionalInfo/"
    ): Result<CmPortalAddition>

    /**
     * 詢問App的所有猜多空活動基本資訊
     */
    suspend fun getActivitiesBaseInfo(
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/GetActivitiesBaseInfo"
    ): Result<GetActivitiesBaseInfo>

    /**
     * 詢問目前猜多空活動狀況
     */
    suspend fun getActivityNowInfo(
        commKey: String,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/GetActivityNowInfo"
    ): Result<GetActivityNowInfo>

    /**
     * 取得會員戰績
     */
    suspend fun getMemberPerformance(
        commKey: String,
        queryGuid: String,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/GetMemberPerformance"
    ): Result<GetMemberPerformance>

    /**
     * 取得排行榜資料
     */
    suspend fun getRanking(
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/GetRanking"
    ): Result<List<GetRanking>>

    /**
     * 取得某人的活動歷史紀錄
     */
    suspend fun getPersonActivityHistory(
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        queryGuid: String,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/GetPersonActivityHistory"
    ): Result<List<GetPersonActivityHistory>>

    /**
     * 詢問會員目前猜多空活動參與狀況(驗證身分)
     */
    suspend fun askMemberForecastStatus(
        commKey: String,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/AskMemberForecastStatus"
    ): Result<AskMemberForecastStatus>

    /**
     * 詢問會員上期猜多空活動參與狀況(驗證身分)
     */
    suspend fun askMemberLastForecastInfo(
        commKey: String,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/AskMemberLastForecastInfo"
    ): Result<AskMemberLastForecastInfo>

    /**
     * 參與活動(驗證身分)
     */
    suspend fun joinActivity(
        commKey: String,
        forecastValue: ForecastValue,
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/JoinActivity"
    ): Result<JoinActivity>

    /**
     * 詢問會員某App上期全部的猜多空活動參與狀況(驗證身分)
     */
    suspend fun askAllMemberLastForecastInfo(
        domain: String = manager.getPortalSettingAdapter().getDomain(),
        url: String = "${domain}CMPortal/api/GuessBullBear/AskAllMemberLastForecastInfo"
    ): Result<AskAllMemberLastForecastInfo>
}