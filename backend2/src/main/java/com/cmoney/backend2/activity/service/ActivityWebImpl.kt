package com.cmoney.backend2.activity.service

import com.cmoney.backend2.activity.extension.checkActivityResponseBody
import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountRequestBody
import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountResponseBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountRequestBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountResponseBody
import com.cmoney.backend2.activity.service.api.requestbonus.RequestBonusRequestBody
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class ActivityWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val activityService: ActivityService,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : ActivityWeb {

    override suspend fun getDayCount(domain: String, url: String): Result<GetDayCountResponseBody> =
        withContext(dispatcherProvider.io()) {
            runCatching {
                activityService.getDayCount(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = GetDayCountRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid()
                    )
                ).checkActivityResponseBody(gson)
            }
        }

    override suspend fun requestBonus(
        referrerId: Long,
        eventId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcherProvider.io()) {
        runCatching {
            activityService.requestBonus(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = RequestBonusRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    referrerId = referrerId,
                    eventId = eventId
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getReferralCount(
        eventId: Long,
        domain: String,
        url: String
    ): Result<GetReferralCountResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            activityService.getReferralCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetReferralCountRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    eventId = eventId
                )
            ).checkResponseBody(gson)
        }
    }
}