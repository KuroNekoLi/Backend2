package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.userbehavior.service.api.common.Event
import com.cmoney.backend2.userbehavior.service.api.log.LogRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class UserBehaviorWebImpl(
    private val gson: Gson,
    private val service: UserBehaviorService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : UserBehaviorWeb {

    override suspend fun uploadReport(
        events: List<Event>,
        processId: String,
        appId: Int,
        platform: Int,
        version: String,
        os: String,
        device: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = LogRequestBody(
                events = events,
                serializedId = processId,
                appId = appId,
                platform = platform,
                version = version,
                os = os,
                device = device
            )
            service.uploadReport(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            ).handleNoContent(gson)
        }
    }
}