package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.customgroup2.service.api.data.Language
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.customgroup2.service.api.data.Stock
import com.cmoney.backend2.customgroup2.service.api.searchstocks.SearchStocksRequestBody
import com.cmoney.backend2.customgroup2.service.api.searchstocksbymarkettype.SearchStocksByMarketTypeRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CustomGroup2WebImpl(
    private val setting: Setting,
    private val gson: Gson,
    private val service: CustomGroup2Service,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : CustomGroup2Web {

    override suspend fun searchStocks(keyword: String, language: Language): Result<List<Stock>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val requestBody = SearchStocksRequestBody(
                    keyword = keyword,
                    language = language.value
                )
                service.searchStocks(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = requestBody
                )
                    .checkResponseBody(gson)
                    .filterNotNull()
                    .map { raw ->
                        Stock(
                            id = raw.id,
                            name = raw.name,
                            marketType = MarketType.fromValue(raw.marketType)
                        )
                    }
            }
        }

    override suspend fun searchStocksByMarketTypes(
        keyword: String,
        language: Language,
        marketTypes: List<MarketType>
    ): Result<List<Stock>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val requestBody = SearchStocksByMarketTypeRequestBody(
                keyword = keyword,
                language = language.value,
                marketTypes = marketTypes.map { type ->
                    type.value
                }
            )
            service.searchStocksByMarketType(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = requestBody
            )
                .checkResponseBody(gson)
                .filterNotNull()
                .map { raw ->
                    Stock(
                        id = raw.id,
                        name = raw.name,
                        marketType = MarketType.fromValue(raw.marketType)
                    )
                }
        }
    }
}