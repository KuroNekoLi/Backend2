package com.cmoney.backend2.portal.service

import com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo.AskAllMemberLastForecastInfoRequestBody
import com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo.AskAllMemberLastForecastInfoWithError
import com.cmoney.backend2.portal.service.api.askmemberforecaststatus.AskMemberForecastStatusRequestBody
import com.cmoney.backend2.portal.service.api.askmemberforecaststatus.AskMemberForecastStatusWithError
import com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo.AskMemberLastForecastInfoRequestBody
import com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo.AskMemberLastForecastInfoWithError
import com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo.GetActivitiesBaseInfoRequestBody
import com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo.GetActivitiesBaseInfoWithError
import com.cmoney.backend2.portal.service.api.getactivitynowinfo.GetActivityNowInfoRequestBody
import com.cmoney.backend2.portal.service.api.getactivitynowinfo.GetActivityNowInfoWithError
import com.cmoney.backend2.portal.service.api.getadditionalinfo.CmPortalAdditionWithError
import com.cmoney.backend2.portal.service.api.getadditionalinfo.GetAdditionRequestBody
import com.cmoney.backend2.portal.service.api.getmemberperformance.GetMemberPerformanceRequestBody
import com.cmoney.backend2.portal.service.api.getmemberperformance.GetMemberPerformanceWithError
import com.cmoney.backend2.portal.service.api.getpersonactivityhistory.GetPersonActivityHistoryRequestBody
import com.cmoney.backend2.portal.service.api.getranking.GetRankingRequestBody
import com.cmoney.backend2.portal.service.api.getsignals.CmPortalSignalWithError
import com.cmoney.backend2.portal.service.api.getsignals.GetSignalRequestBody
import com.cmoney.backend2.portal.service.api.gettarget.CmPortalTargetWithError
import com.cmoney.backend2.portal.service.api.gettarget.GetTargetRequestBody
import com.cmoney.backend2.portal.service.api.joinactivity.JoinActivityRequestBody
import com.cmoney.backend2.portal.service.api.joinactivity.JoinActivityWithError
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PortalService {

    /**
     * 服務1. 取得選股監控對象股票清單
     */
    @POST("cmportal/api/pickstock/gettarget/")
    suspend fun getTarget(
        @Header("Authorization") authorization: String,
        @Body body: GetTargetRequestBody
    ): Response<CmPortalTargetWithError>

    /**
     * 服務2. 取得選股全部條件篩選狀態
     */
    @POST("cmportal/api/pickstock/getsignals/")
    suspend fun getSignals(
        @Header("Authorization") authorization: String,
        @Body body: GetSignalRequestBody
    ): Response<CmPortalSignalWithError>

    /**
     * 服務3. 取得顯示資訊欄位資料
     */
    @POST("cmportal/api/additionalinformation/getadditionalInfo/")
    suspend fun getAdditionalInfo(
        @Header("Authorization") authorization: String,
        @Body body: GetAdditionRequestBody
    ): Response<CmPortalAdditionWithError>

    /**
     * 詢問App的所有猜多空活動基本資訊
     *
     */
    @POST("CMPortal/api/GuessBullBear/GetActivitiesBaseInfo")
    suspend fun getActivitiesBaseInfo(
        @Header("Authorization") authorization: String,
        @Body body: GetActivitiesBaseInfoRequestBody
    ): Response<GetActivitiesBaseInfoWithError>

    /**
     * 詢問目前猜多空活動狀況
     *
     */
    @POST("CMPortal/api/GuessBullBear/GetActivityNowInfo")
    suspend fun getActivityNowInfo(
        @Header("Authorization") authorization: String,
        @Body body: GetActivityNowInfoRequestBody
    ): Response<GetActivityNowInfoWithError>

    /**
     * 取得會員戰績
     */
    @POST("CMPortal/api/GuessBullBear/GetMemberPerformance")
    suspend fun getMemberPerformance(
        @Header("Authorization") authorization: String,
        @Body body: GetMemberPerformanceRequestBody
    ): Response<GetMemberPerformanceWithError>

    /**
     * 取得排行榜資料
     *
     */
    @POST("CMPortal/api/GuessBullBear/GetRanking")
    suspend fun getRanking(
        @Header("Authorization") authorization: String,
        @Body body: GetRankingRequestBody
    ): Response<JsonElement>

    /**
     * 取得某人的活動歷史紀錄
     *
     */
    @POST("CMPortal/api/GuessBullBear/GetPersonActivityHistory")
    suspend fun getPersonActivityHistory(
        @Header("Authorization") authorization: String,
        @Body body: GetPersonActivityHistoryRequestBody
    ): Response<JsonElement>

    /**
     * 詢問會員目前猜多空活動參與狀況(驗證身分)
     *
     */
    @POST("CMPortal/api/GuessBullBear/AskMemberForecastStatus")
    suspend fun askMemberForecastStatus(
        @Header("Authorization") authorization: String,
        @Body body: AskMemberForecastStatusRequestBody
    ): Response<AskMemberForecastStatusWithError>

    /**
     * 詢問會員上期猜多空活動參與狀況(驗證身分)
     */
    @POST("CMPortal/api/GuessBullBear/AskMemberLastForecastInfo")
    suspend fun askMemberLastForecastInfo(
        @Header("Authorization") authorization: String,
        @Body body: AskMemberLastForecastInfoRequestBody
    ): Response<AskMemberLastForecastInfoWithError>

    /**
     * 參與活動(驗證身分)
     */
    @POST("CMPortal/api/GuessBullBear/JoinActivity")
    suspend fun joinActivity(
        @Header("Authorization") authorization: String,
        @Body body: JoinActivityRequestBody
    ): Response<JoinActivityWithError>

    /**
     * 詢問會員某App上期全部的猜多空活動參與狀況(驗證身分)
     */
    @POST("CMPortal/api/GuessBullBear/AskAllMemberLastForecastInfo")
    suspend fun askAllMemberLastForecastInfo(
        @Header("Authorization") authorization: String,
        @Body body: AskAllMemberLastForecastInfoRequestBody
    ): Response<AskAllMemberLastForecastInfoWithError>
}