package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.extension.asRequestHeader
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.customgroup2.service.api.data.RequestMarketType
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

    override suspend fun searchStocks(keyword: String, languages: List<Language>): Result<List<Stock>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val requestBody = SearchStocksRequestBody(keyword = keyword)
                service.searchStocks(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    language = languages.asRequestHeader(),
                    requestBody = requestBody
                )
                    .checkResponseBody(gson)
                    .filterNotNull()
                    .map { raw ->
                        Stock(
                            id = raw.id,
                            name = raw.name,
                            marketType = raw.marketType?.let { marketType ->
                                MarketType.fromInt(marketType)
                            }
                        )
                    }
            }
        }

    override suspend fun searchStocksByMarketTypes(
        keyword: String,
        languages: List<Language>,
        marketTypes: List<MarketType>
    ): Result<List<Stock>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val requestBody = SearchStocksByMarketTypeRequestBody(
                keyword = keyword,
                marketTypes = marketTypes.map { type ->
                    RequestMarketType(
                        marketType = type.value,
                        types = type.types.map { subType ->
                            subType.value
                        }
                    )
                }
            )
            service.searchStocksByMarketType(
                authorization = setting.accessToken.createAuthorizationBearer(),
                language = languages.asRequestHeader(),
                requestBody = requestBody
            )
                .checkResponseBody(gson)
                .filterNotNull()
                .map { raw ->
                    Stock(
                        id = raw.id,
                        name = raw.name,
                        marketType = raw.marketType?.let { marketType ->
                            MarketType.fromInt(marketType)
                        }
                    )
                }
        }
    }
}