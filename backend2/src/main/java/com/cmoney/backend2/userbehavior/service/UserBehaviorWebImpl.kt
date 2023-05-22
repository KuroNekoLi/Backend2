package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.userbehavior.service.api.common.Event
import com.cmoney.backend2.userbehavior.service.api.log.LogRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class UserBehaviorWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: UserBehaviorService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : UserBehaviorWeb {

    override suspend fun uploadReport(
        events: List<Event>,
        processId: String?,
        appId: Int,
        platform: Int,
        version: String,
        os: String?,
        device: String?,
        domain: String,
        url: String
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
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = requestBody
            ).handleNoContent(gson)
        }
    }
}