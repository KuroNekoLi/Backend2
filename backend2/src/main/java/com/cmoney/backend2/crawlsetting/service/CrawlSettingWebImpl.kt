package com.cmoney.backend2.crawlsetting.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.crawlsetting.service.api.getcathaycastatus.GetCathayCaStatusRequestBody
import com.cmoney.backend2.crawlsetting.service.api.gettaishincastatus.GetTaishinCaStatusRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CrawlSettingWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: CrawlSettingService,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider,
) : CrawlSettingWeb {

    override suspend fun getCathayCaStatus(
        userInfoKey: String,
        domain: String,
        url: String
    ): Result<String> = withContext(dispatcherProvider.io()) {
        runCatching {
            val requestBody = GetCathayCaStatusRequestBody(userInfoKey = userInfoKey)
            service.getCathayCaStatus(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getTaishinCaStatus(
        userInfoKey: String,
        domain: String,
        url: String
    ): Result<String> = withContext(dispatcherProvider.io()) {
        runCatching {
            val requestBody = GetTaishinCaStatusRequestBody(userInfoKey = userInfoKey)
            service.getTaishinCaStatus(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }

}
