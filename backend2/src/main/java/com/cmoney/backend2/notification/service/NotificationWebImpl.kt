package com.cmoney.backend2.notification.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notification.service.api.devicetoken.updateguesttoken.UpdateGuestTokenRequestBody
import com.cmoney.backend2.notification.service.api.devicetoken.updatemembertoken.UpdateMemberTokenRequestBody
import com.cmoney.backend2.notification.service.api.statistics.updatearrived.UpdateArrivedRequestBody
import com.cmoney.backend2.notification.service.api.statistics.updateclickcount.UpdateClickedCountRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class NotificationWebImpl(
    private val gson: Gson,
    private val service: NotificationService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : NotificationWeb {

    override suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.updateArriveCount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = UpdateArrivedRequestBody(
                    sn = sn,
                    appId = setting.appId,
                    deviceToken = pushToken,
                    title = title,
                    content = content,
                    platform = setting.platform.code,
                    analyticsId = analyticsId
                )
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.updateClickCount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = UpdateClickedCountRequestBody(
                    sn = sn,
                    appId = setting.appId,
                    deviceToken = pushToken,
                    title = title,
                    content = content,
                    platform = setting.platform.code,
                    analyticsId = analyticsId
                )
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun updateGuestPushToken(pushToken: String): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateGuestPushToken(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = UpdateGuestTokenRequestBody(
                        appId = setting.appId,
                        deviceToken = pushToken,
                        platform = setting.platform.code,
                        version = setting.appVersion
                    )
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun updateMemberPushToken(pushToken: String): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateMemberPushToken(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = UpdateMemberTokenRequestBody(
                        appId = setting.appId,
                        deviceToken = pushToken,
                        platform = setting.platform.code,
                        version = setting.appVersion
                    )
                )
                response.handleNoContent(gson)
            }
        }
}

