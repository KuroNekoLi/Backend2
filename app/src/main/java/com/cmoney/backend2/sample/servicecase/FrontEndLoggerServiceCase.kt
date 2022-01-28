package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerWeb
import com.cmoney.backend2.frontendlogger.service.api.LogRequest
import com.cmoney.backend2.frontendlogger.service.api.LogRequestBody
import com.cmoney.backend2.frontendlogger.service.api.LogResponse
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class FrontEndLoggerServiceCase : ServiceCase {

    private val web by inject<FrontEndLoggerWeb>()

    override suspend fun testAll() {
        val responseTimeInMillis = System.currentTimeMillis()
        val requestTimeInMillis = responseTimeInMillis - TimeUnit.SECONDS.toMillis(5)

        val body = listOf(
            LogRequestBody(
                request = LogRequest(
                    timeInMillis = requestTimeInMillis,
                    httpMethod = null,
                    domain = null,
                    path = null,
                    header = null,
                    query = null,
                    body = null
                ),
                response = LogResponse(
                    timeInMillis = responseTimeInMillis,
                    httpStatusCode = 200
                ),
                additionalInfo = mapOf(
                    "k1" to "v1",
                    "k2" to "v2"
                ),
                message = "testing service from android backend2 sample"
            )
        )

        web.log(body = body)
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "FrontEndLogger"
    }

}
