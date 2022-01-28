package com.cmoney.backend2.frontendlogger.service

import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.frontendlogger.service.api.LogRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class FrontEndLoggerWebImpl(
    override val baseHost: String,
    private val service: FrontEndLoggerService,
    private val setting: Setting,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : FrontEndLoggerWeb {

    override suspend fun log(
        body: List<LogRequestBody>,
        indexName: String,
        host: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val requestUrl = "${host}frontendlogger/log/$indexName"

                service.log(
                    url = requestUrl,
                    authToken = setting.accessToken.createAuthorizationBearer(),
                    body = body
                )
                    .handleNoContent(gson)
            }
        }

}
