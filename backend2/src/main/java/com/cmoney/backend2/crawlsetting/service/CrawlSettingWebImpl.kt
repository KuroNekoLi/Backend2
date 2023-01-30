package com.cmoney.backend2.crawlsetting.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.crawlsetting.service.api.getcathaycastatus.GetCathayCaStatusRequestBody
import com.cmoney.backend2.crawlsetting.service.api.gettaishincastatus.GetTaishinCaStatusRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CrawlSettingWebImpl(
    override val baseHost: String,
    private val setting: Setting,
    private val service: CrawlSettingService,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : CrawlSettingWeb {

    override suspend fun getCathayCaStatus(userInfoKey: String, host: String): Result<String> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val requestUrl = "${host}CrawlSettingService/cathaycastatus"
                val requestBody = GetCathayCaStatusRequestBody(userInfoKey = userInfoKey)
                service.getCathayCaStatus(
                    url = requestUrl,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = requestBody
                )
                    .checkResponseBody(gson)
            }
        }

    override suspend fun getTaishinCaStatus(userInfoKey: String, host: String): Result<String> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val requestUrl = "${host}CrawlSettingService/taishincastatus"
                val requestBody = GetTaishinCaStatusRequestBody(userInfoKey = userInfoKey)
                service.getTaishinCaStatus(
                    url = requestUrl,
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = requestBody
                )
                    .checkResponseBody(gson)
            }
        }

}
