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
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityWebImpl(
    private val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val activityService: ActivityService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ActivityWeb {

    override suspend fun getDayCount(
        apiParam: MemberApiParam
    ): Result<GetDayCountResponseBody> = getDayCount()

    override suspend fun getDayCount(): Result<GetDayCountResponseBody> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                activityService.getDayCount(
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = GetDayCountRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid()
                    )
                ).checkActivityResponseBody(gson)
            }
        }

    override suspend fun requestBonus(
        apiParam: MemberApiParam,
        referrerId: Long,
        eventId: Long): Result<Unit> = requestBonus(referrerId, eventId)

    override suspend fun requestBonus(referrerId: Long, eventId: Long): Result<Unit> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                activityService.requestBonus(
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
        apiParam: MemberApiParam,
        eventId: Long
    ): Result<GetReferralCountResponseBody> = getReferralCount(eventId)

    override suspend fun getReferralCount(eventId: Long): Result<GetReferralCountResponseBody> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                activityService.getReferralCount(
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