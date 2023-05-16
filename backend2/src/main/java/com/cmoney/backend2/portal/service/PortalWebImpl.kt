package com.cmoney.backend2.portal.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toJsonArrayWithErrorResponse
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import kotlinx.coroutines.withContext

class PortalWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: PortalService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : PortalWeb {

    override suspend fun getTarget(
        domain: String,
        url: String
    ): Result<CmPortalTarget> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getTarget(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetTargetRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getSignals(
        domain: String,
        url: String
    ): Result<CmPortalSignal> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getSignals(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetSignalRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAdditionalInfo(
        settingId: Int,
        domain: String,
        url: String
    ): Result<CmPortalAddition> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getAdditionalInfo(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GetAdditionRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid(),
                        settingId = settingId
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getActivitiesBaseInfo(
        domain: String,
        url: String
    ): Result<GetActivitiesBaseInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getActivitiesBaseInfo(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GetActivitiesBaseInfoRequestBody(manager.getAppId())
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getActivityNowInfo(
        commKey: String,
        domain: String,
        url: String
    ): Result<GetActivityNowInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getActivityNowInfo(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GetActivityNowInfoRequestBody(
                        manager.getAppId(),
                        commKey
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getMemberPerformance(
        commKey: String,
        queryGuid: String,
        domain: String,
        url: String
    ): Result<GetMemberPerformance> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getMemberPerformance(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetMemberPerformanceRequestBody(
                    manager.getAppId(),
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

    override suspend fun getRanking(
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        domain: String,
        url: String
    ): Result<List<GetRanking>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getRanking(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetRankingRequestBody(
                    appId = manager.getAppId(),
                    commKey = commKey,
                    fetchCount = fetchCount,
                    skipCount = skipCount
                )
            )
            response.toJsonArrayWithErrorResponse<List<GetRanking>>(gson = gson)
        }
    }

    override suspend fun getPersonActivityHistory(
        commKey: String,
        fetchCount: Int,
        skipCount: Int,
        queryGuid: String,
        domain: String,
        url: String
    ): Result<List<GetPersonActivityHistory>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getPersonActivityHistory(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetPersonActivityHistoryRequestBody(
                    appId = manager.getAppId(),
                    commKey = commKey,
                    fetchCount = fetchCount,
                    skipCount = skipCount,
                    queryGuid = queryGuid
                )
            )
            response.toJsonArrayWithErrorResponse<List<GetPersonActivityHistory>>(gson = gson)
        }
    }

    override suspend fun askMemberForecastStatus(
        commKey: String,
        domain: String,
        url: String
    ): Result<AskMemberForecastStatus> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.askMemberForecastStatus(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = AskMemberForecastStatusRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid(),
                        commKey = commKey
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun askMemberLastForecastInfo(
        commKey: String,
        domain: String,
        url: String
    ): Result<AskMemberLastForecastInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.askMemberLastForecastInfo(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = AskMemberLastForecastInfoRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid(),
                        commKey = commKey
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun joinActivity(
        commKey: String,
        forecastValue: ForecastValue,
        domain: String,
        url: String
    ): Result<JoinActivity> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.joinActivity(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = JoinActivityRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun askAllMemberLastForecastInfo(
        domain: String,
        url: String
    ): Result<AskAllMemberLastForecastInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.askAllMemberLastForecastInfo(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = AskAllMemberLastForecastInfoRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid()
                    )
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }
}