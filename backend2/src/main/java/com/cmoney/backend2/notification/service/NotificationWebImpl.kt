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

    @Deprecated("Will remove in 2023/1/1", replaceWith = ReplaceWith("this.updateArriveCount(sn, pushToken, title, content, emptyList(), 0)"))
    override suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String,
    ): Result<Unit> = updateArriveCount(
        sn = sn,
        pushToken = pushToken,
        title = title,
        content = content,
        analyticsLabels = emptyList(),
        createTime = 0L
    )

    override suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String>,
        createTime: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateArriveCount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = UpdateArrivedRequestBody(
                    sn = sn,
                    appId = setting.appId,
                    deviceToken = pushToken,
                    title = title,
                    content = content,
                    platform = setting.platform.code,
                    analyticsLabels = analyticsLabels,
                    createTime = createTime
                )
            )
            response.handleNoContent(gson)
        }
    }

    @Deprecated("Will remove in 2023/1/1", replaceWith = ReplaceWith("this.updateClickCount(sn, pushToken, title, content, emptyList(), 0)"))
    override suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
    ): Result<Unit> = updateClickCount(
        sn = sn,
        pushToken = pushToken,
        title = title,
        content = content,
        analyticsLabels = emptyList(),
        createTime = 0L
    )

    override suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String>,
        createTime: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateClickCount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = UpdateClickedCountRequestBody(
                    sn = sn,
                    appId = setting.appId,
                    deviceToken = pushToken,
                    title = title,
                    content = content,
                    platform = setting.platform.code,
                    analyticsLabels = analyticsLabels,
                    createTime = createTime
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

