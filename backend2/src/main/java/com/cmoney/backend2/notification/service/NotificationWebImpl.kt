package com.cmoney.backend2.notification.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.notification.service.api.devicetoken.updateguesttoken.UpdateGuestTokenRequestBody
import com.cmoney.backend2.notification.service.api.devicetoken.updatemembertoken.UpdateMemberTokenRequestBody
import com.cmoney.backend2.notification.service.api.statistics.updatearrived.UpdateArrivedRequestBody
import com.cmoney.backend2.notification.service.api.statistics.updateclickcount.UpdateClickedCountRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class NotificationWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: NotificationService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : NotificationWeb {

    override suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String>,
        createTime: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.updateArriveCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = UpdateArrivedRequestBody(
                    sn = sn,
                    appId = manager.getAppId(),
                    deviceToken = pushToken,
                    title = title,
                    content = content,
                    platform = manager.getPlatform().code,
                    analyticsLabels = analyticsLabels,
                    createTime = createTime
                )
            )
            response.handleNoContent(gson)
        }
    }

    override suspend fun updateGuestPushToken(
        pushToken: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateGuestPushToken(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = UpdateGuestTokenRequestBody(
                        appId = manager.getAppId(),
                        deviceToken = pushToken,
                        platform = manager.getPlatform().code,
                        version = manager.getAppVersionName()
                    )
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun updateMemberPushToken(
        pushToken: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.updateMemberPushToken(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = UpdateMemberTokenRequestBody(
                        appId = manager.getAppId(),
                        deviceToken = pushToken,
                        platform = manager.getPlatform().code,
                        version = manager.getAppVersionName()
                    )
                )
                response.handleNoContent(gson)
            }
        }

    override suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String>,
        createTime: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateClickCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = UpdateClickedCountRequestBody(
                    sn = sn,
                    appId = manager.getAppId(),
                    deviceToken = pushToken,
                    title = title,
                    content = content,
                    platform = manager.getPlatform().code,
                    analyticsLabels = analyticsLabels,
                    createTime = createTime
                )
            )
            response.handleNoContent(gson)
        }
    }
}

