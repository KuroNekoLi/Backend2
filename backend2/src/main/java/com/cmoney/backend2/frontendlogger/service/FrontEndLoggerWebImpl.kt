package com.cmoney.backend2.frontendlogger.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.frontendlogger.service.api.LogRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class FrontEndLoggerWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: FrontEndLoggerService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : FrontEndLoggerWeb {

    override suspend fun log(
        body: List<LogRequestBody>,
        indexName: String,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.log(
                    url = url,
                    authToken = manager.getAccessToken().createAuthorizationBearer(),
                    body = body
                ).handleNoContent(gson)
            }
        }

}
