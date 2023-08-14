package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.virtualassets.service.api.exchange.ExchangeRequestBody
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeRequestBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class VirtualAssetsWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: VirtualAssetsService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : VirtualAssetsWeb {

    override suspend fun getExchangeProductList(
        domain: String,
        url: String
    ): Result<GetExchangeProductListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getExchangeProductList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getGroupLastExchangeTime(
        exchangeIds: List<Long>,
        domain: String,
        url: String
    ): Result<GetGroupLastExchangeTimeResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getGroupLastExchangeTime(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetGroupLastExchangeTimeRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    exchangeIds = exchangeIds
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun exchange(exchangeId: Long, domain: String, url: String): Result<Unit> =
        withContext(dispatcher.io()) {
            runCatching {
                service.exchange(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = ExchangeRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid()
                    )
                ).handleNoContent(gson)
            }
        }
}