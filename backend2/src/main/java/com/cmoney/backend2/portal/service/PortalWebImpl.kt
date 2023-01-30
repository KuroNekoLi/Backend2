package com.cmoney.backend2.portal.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo.AskAllMemberLastForecastInfo
import com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo.AskAllMemberLastForecastInfoRequestBody
import com.cmoney.backend2.portal.service.api.askmemberforecaststatus.AskMemberForecastStatus
import com.cmoney.backend2.portal.service.api.askmemberforecaststatus.AskMemberForecastStatusRequestBody
import com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo.AskMemberLastForecastInfo
import com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo.AskMemberLastForecastInfoRequestBody
import com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo.GetActivitiesBaseInfo
import com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo.GetActivitiesBaseInfoRequestBody
import com.cmoney.backend2.portal.service.api.getactivitynowinfo.GetActivityNowInfo
import com.cmoney.backend2.portal.service.api.getactivitynowinfo.GetActivityNowInfoRequestBody
import com.cmoney.backend2.portal.service.api.getadditionalinfo.CmPortalAddition
import com.cmoney.backend2.portal.service.api.getadditionalinfo.GetAdditionRequestBody
import com.cmoney.backend2.portal.service.api.getmemberperformance.GetMemberPerformance
import com.cmoney.backend2.portal.service.api.getmemberperformance.GetMemberPerformanceRequestBody
import com.cmoney.backend2.portal.service.api.getpersonactivityhistory.GetPersonActivityHistory
import com.cmoney.backend2.portal.service.api.getpersonactivityhistory.GetPersonActivityHistoryRequestBody
import com.cmoney.backend2.portal.service.api.getranking.GetRanking
import com.cmoney.backend2.portal.service.api.getranking.GetRankingRequestBody
import com.cmoney.backend2.portal.service.api.getsignals.CmPortalSignal
import com.cmoney.backend2.portal.service.api.getsignals.GetSignalRequestBody
import com.cmoney.backend2.portal.service.api.gettarget.CmPortalTarget
import com.cmoney.backend2.portal.service.api.gettarget.GetTargetRequestBody
import com.cmoney.backend2.portal.service.api.joinactivity.JoinActivity
import com.cmoney.backend2.portal.service.api.joinactivity.JoinActivityRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

class PortalWebImpl(
    private val gson: Gson,
    private val service: PortalService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : PortalWeb {

    /**
     * 服務1. 取得選股監控對象股票清單
     */
    override suspend fun getTarget(
        apiParam: MemberApiParam
    ): Result<CmPortalTarget> = getTarget()

    override suspend fun getTarget(): Result<CmPortalTarget> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getTarget(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetTargetRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務2. 取得選股全部條件篩選狀態
     */
    override suspend fun getSignals(
        apiParam: MemberApiParam
    ): Result<CmPortalSignal> = getSignals()

    override suspend fun getSignals(): Result<CmPortalSignal> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getSignals(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetSignalRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務3. 取得顯示資訊欄位資料
     */
    override suspend fun getAdditionalInfo(
        apiParam: MemberApiParam,
        settingId: Int
    ): Result<CmPortalAddition> = getAdditionalInfo(settingId)

    override suspend fun getAdditionalInfo(settingId: Int): Result<CmPortalAddition> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getAdditionalInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetAdditionRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid(),
                        settingId = settingId
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 詢問App的所有猜多空活動基本資訊
     *
     * @param apiParam
     * @return
     */
    override suspend fun getActivitiesBaseInfo(
        apiParam: MemberApiParam
    ): Result<GetActivitiesBaseInfo> = getActivitiesBaseInfo()

    override suspend fun getActivitiesBaseInfo(): Result<GetActivitiesBaseInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getActivitiesBaseInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetActivitiesBaseInfoRequestBody(setting.appId)
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 詢問目前猜多空活動狀況
     *
     */
    override suspend fun getActivityNowInfo(
        apiParam: MemberApiParam,
        commKey: String
    ): Result<GetActivityNowInfo> = getActivityNowInfo(commKey)

    override suspend fun getActivityNowInfo(commKey: String): Result<GetActivityNowInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getActivityNowInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetActivityNowInfoRequestBody(
                        setting.appId,
                        commKey
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得會員戰績
     *
     */
    override suspend fun getMemberPerformance(
        apiParam: MemberApiParam,
        commKey: String,
        queryGuid: String
    ): Result<GetMemberPerformance> = getMemberPerformance(commKey, queryGuid)

    override suspend fun getMemberPerformance(
        commKey: String,
        queryGuid: String
    ): Result<GetMemberPerformance> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getMemberPerformance(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetMemberPerformanceRequestBody(
                    setting.appId,
                    commKey,
                    queryGuid
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 取得排行榜資料
     */
    override suspend fun getRanking(
        apiParam: MemberApiParam,
        commKey: String,
        fetchCount: Int,
        skipCount: Int
    ): Result<List<GetRanking>> = getRanking(commKey, fetchCount, skipCount)

    override suspend fun getRanking(
        commKey: String,
        fetchCount: Int,
        skipCount: Int
    ): Result<List<GetRanking>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getRanking(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetRankingRequestBody(
                    appId = setting.appId,
                    commKey = commKey,
                    fetchCount = fetchCount,
                    skipCount = skipCount
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .toJsonArrayWithErrorResponse<List<GetRanking>>()

        }
    }

    /**
     * 取得某人的活動歷史紀錄
     */
    override suspend fun getPersonActivityHistory(
        apiParam: MemberApiParam,
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        queryGuid: String
    ): Result<List<GetPersonActivityHistory>> =
        getPersonActivityHistory(commKey, fetchCount, skipCount, queryGuid)

    override suspend fun getPersonActivityHistory(
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        queryGuid: String
    ): Result<List<GetPersonActivityHistory>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getPersonActivityHistory(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetPersonActivityHistoryRequestBody(
                    appId = setting.appId,
                    commKey = commKey,
                    fetchCount = fetchCount,
                    skipCount = skipCount,
                    queryGuid = queryGuid
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .toJsonArrayWithErrorResponse<List<GetPersonActivityHistory>>()
        }
    }

    /**
     * 詢問會員目前猜多空活動參與狀況(驗證身分)
     */
    override suspend fun askMemberForecastStatus(
        apiParam: MemberApiParam,
        commKey: String
    ): Result<AskMemberForecastStatus> = askMemberForecastStatus(commKey)

    override suspend fun askMemberForecastStatus(commKey: String): Result<AskMemberForecastStatus> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.askMemberForecastStatus(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = AskMemberForecastStatusRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid(),
                        commKey = commKey
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 詢問會員上期猜多空活動參與狀況(驗證身分)
     *
     */
    override suspend fun askMemberLastForecastInfo(
        apiParam: MemberApiParam,
        commKey: String
    ): Result<AskMemberLastForecastInfo> = askMemberLastForecastInfo(commKey)

    override suspend fun askMemberLastForecastInfo(commKey: String): Result<AskMemberLastForecastInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.askMemberLastForecastInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = AskMemberLastForecastInfoRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid(),
                        commKey = commKey
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 參與活動(驗證身分)
     */
    override suspend fun joinActivity(
        apiParam: MemberApiParam,
        commKey: String,
        forecastValue: ForecastValue
    ): Result<JoinActivity> = joinActivity(commKey, forecastValue)

    override suspend fun joinActivity(
        commKey: String,
        forecastValue: ForecastValue
    ): Result<JoinActivity> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.joinActivity(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = JoinActivityRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    commKey = commKey,
                    forecastValue = forecastValue
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 詢問會員某App上期全部的猜多空活動參與狀況(驗證身分)
     *
     * @param apiParam
     * @return
     */
    override suspend fun askAllMemberLastForecastInfo(apiParam: MemberApiParam): Result<AskAllMemberLastForecastInfo> =
        askAllMemberLastForecastInfo()

    override suspend fun askAllMemberLastForecastInfo(): Result<AskAllMemberLastForecastInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.askAllMemberLastForecastInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = AskAllMemberLastForecastInfoRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 處理正常回傳是JsonArray 發生錯誤是JsonObject 的api回傳
     *
     * @param T
     * @return
     */
    @Throws(ServerException::class)
    private inline fun <reified T> JsonElement.toJsonArrayWithErrorResponse(): T {
        val responseResult = if (this.isJsonArray) {
            try {
                gson.fromJson<T>(this, object : TypeToken<T>() {}.type)
            } catch (exception: JsonSyntaxException) {
                null
            }
        } else {
            null
        }

        return if (responseResult != null) {
            responseResult
        } else {
            val error = gson.fromJson<CMoneyError>(this, object : TypeToken<CMoneyError>() {}.type)
            throw ServerException(
                error.detail?.code ?: Constant.SERVICE_ERROR_CODE,
                error.detail?.message.orEmpty()
            )
        }
    }
}