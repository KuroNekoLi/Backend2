package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.virtualassets.service.api.exchange.ExchangeRequestBody
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeRequestBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class VirtualAssetsWebImpl(
    private val setting: Setting,
    private val gson: Gson,
    private val virtualAssetsService: VirtualAssetsService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : VirtualAssetsWeb {

    override suspend fun getExchangeProductList(
        apiParam: MemberApiParam
    ): Result<GetExchangeProductListResponseBody> = getExchangeProductList()

    /**
     * 取得可兌換商品清單
     */
    override suspend fun getExchangeProductList(): Result<GetExchangeProductListResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                virtualAssetsService.getExchangeProductList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    pathAppId = setting.appId
                ).checkResponseBody(gson)
            }
        }

    override suspend fun getGroupLastExchangeTime(
        exchangeIds: List<Long>,
        apiParam: MemberApiParam
    ): Result<GetGroupLastExchangeTimeResponseBody> = getGroupLastExchangeTime(exchangeIds)

    override suspend fun getGroupLastExchangeTime(
        exchangeIds: List<Long>
    ): Result<GetGroupLastExchangeTimeResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            virtualAssetsService.getGroupLastExchangeTime(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetGroupLastExchangeTimeRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    exchangeIds = exchangeIds
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun exchange(
        exchangeId: Long,
        apiParam: MemberApiParam
    ): Result<Unit> = exchange(exchangeId)

    /**
     * 會員兌換指定商品
     *
     * @param exchangeId 兌換編號
     */
    override suspend fun exchange(
        exchangeId: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            virtualAssetsService.exchange(
                authorization = setting.accessToken.createAuthorizationBearer(),
                pathExchangeId = exchangeId,
                requestBody = ExchangeRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).handleNoContent(gson)
        }
    }
}