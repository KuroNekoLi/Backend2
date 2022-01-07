package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.customgroup2.service.api.data.*
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

    override suspend fun searchStocks(
        keyword: String,
        languages: List<Language>
    ): Result<List<Stock>> = searchStocksV2(keyword, languages)
        .map { stocksV2 ->
            stocksV2.map { stockV2 ->
                val marketType = stockV2.marketType?.type?.let { type ->
                    MarketType.fromInt(type)
                }
                Stock(
                    id = stockV2.id,
                    name = stockV2.name,
                    marketType = marketType
                )
            }
        }

    override suspend fun searchStocksV2(
        keyword: String,
        languages: List<Language>
    ): Result<List<StockV2>> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = SearchStocksRequestBody(keyword = keyword)
            service.searchStocks(
                authorization = setting.accessToken.createAuthorizationBearer(),
                language = languages.asRequestHeader(),
                requestBody = requestBody
            )
                .checkResponseBody(gson)
                .filterNotNull()
                .map { stock ->
                    val type = stock.marketType
                    val subType = stock.type
                    var marketType: MarketTypeV2? = null
                    if (type != null && subType != null) {
                        marketType = MarketTypeV2.valueOf(type, subType)
                    }
                    StockV2(
                        id = stock.id,
                        name = stock.name,
                        marketType = marketType
                    )
                }
        }
    }

    override suspend fun searchStocksByMarketTypes(
        keyword: String,
        languages: List<Language>,
        marketTypes: List<MarketType>
    ): Result<List<Stock>> = withContext(dispatcher.io()) {
        val marketTypeV2 = marketTypes.flatMap { marketType ->
            val type = marketType.value
            val subTypes = marketType.types
            subTypes.map { subType ->
                MarketTypeV2.valueOf(type, subType.value)
            }
        }.filterNotNull()
        searchStocksByMarketTypesV2(
            keyword = keyword,
            languages = languages,
            marketTypes = marketTypeV2
        ).map { stocksV2 ->
            stocksV2.map { stockV2 ->
                val marketType = stockV2.marketType?.type?.let { type ->
                    MarketType.fromInt(type)
                }
                Stock(
                    id = stockV2.id,
                    name = stockV2.name,
                    marketType = marketType
                )
            }
        }
    }

    override suspend fun searchStocksByMarketTypesV2(
        keyword: String,
        languages: List<Language>,
        marketTypes: List<MarketTypeV2>
    ): Result<List<StockV2>> = withContext(dispatcher.io()) {
        runCatching {
            val marketTypeMap = marketTypes.groupBy { marketType ->
                marketType.type
            }
            val requestMarketTypes = marketTypeMap.map { entry ->
                val type = entry.key
                val subTypes = entry.value.map {
                    it.subType
                }
                RequestMarketType(marketType = type, types = subTypes)
            }
            val requestBody = SearchStocksByMarketTypeRequestBody(
                keyword = keyword,
                marketTypes = requestMarketTypes
            )
            service.searchStocksByMarketType(
                authorization = setting.accessToken.createAuthorizationBearer(),
                language = languages.asRequestHeader(),
                requestBody = requestBody
            )
                .checkResponseBody(gson)
                .filterNotNull()
                .map { stock ->
                    val type = stock.marketType
                    val subType = stock.type
                    var marketType: MarketTypeV2? = null
                    if (type != null && subType != null) {
                        marketType = MarketTypeV2.valueOf(type, subType)
                    }
                    StockV2(
                        id = stock.id,
                        name = stock.name,
                        marketType = marketType
                    )
                }
        }
    }

    override suspend fun getCustomGroup(marketType: DocMarketType): Result<List<CustomGroup>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val docTypeCondition = "type:StockGroup"
                val marketTypeCondition = "${DocMarketType.KEY}:${marketType.value}"
                val filters = listOf(docTypeCondition, marketTypeCondition)
                service.getCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    filters = filters.joinToString(",")
                )
                    .checkResponseBody(gson)
                    .documents?.mapNotNull { document ->
                        val id = document.id ?: return@mapNotNull null
                        val responseMarketType = document.marketType?.let { type ->
                            DocMarketType.fromValue(type)
                        }
                        CustomGroup(
                            id = id,
                            name = document.displayName,
                            marketType = responseMarketType,
                            stocks = document.stocks
                        )
                    }
                    .orEmpty()
            }
        }

    override suspend fun getCustomGroup(id: String): Result<CustomGroup> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val document = service.getCustomGroupBy(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    id = id
                )
                    .checkResponseBody(gson)
                val responseMarketType = document.marketType?.let { type ->
                    DocMarketType.fromValue(type)
                }
                CustomGroup(
                    id = id,
                    name = document.displayName,
                    marketType = responseMarketType,
                    stocks = document.stocks
                )
            }
        }

    override suspend fun updateCustomGroup(newGroup: CustomGroup): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val newDocument = Document(
                    id = newGroup.id,
                    displayName = newGroup.name,
                    marketType = newGroup.marketType?.value,
                    stocks = newGroup.stocks
                )
                service.updateCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    id = newGroup.id,
                    newGroup = newDocument
                )
                    .handleNoContent(gson)
            }
        }

    override suspend fun createCustomGroup(
        displayName: String,
        marketType: DocMarketType
    ): Result<CustomGroup> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val baseDocument = Document(
                    displayName = displayName,
                    marketType = marketType.value,
                    stocks = emptyList()
                )
                val groupId = service.createCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    baseDocument = baseDocument
                )
                    .checkResponseBody(gson)
                    .id ?: throw IllegalStateException("id return is null")
                CustomGroup(
                    id = groupId,
                    name = displayName,
                    marketType = marketType,
                    stocks = emptyList()
                )
            }
        }

    override suspend fun deleteCustomGroup(id: String): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.deleteCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    id = id
                )
                    .handleNoContent(gson)
            }
        }

    override suspend fun getUserConfiguration(): Result<UserConfiguration> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val userConfigurationDocument = service.getUserConfiguration(
                    authorization = setting.accessToken.createAuthorizationBearer()
                )
                    .checkResponseBody(gson)
                UserConfiguration(
                    customGroupOrders = userConfigurationDocument.documentOrders
                )
            }
        }

    override suspend fun updateConfiguration(customGroups: List<CustomGroup>): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val newConfiguration = UserConfigurationDocument(
                    documentOrders = customGroups.mapIndexed { index, customGroup ->
                        customGroup.id to (index + 1)
                    }.toMap()
                )
                service.updateUserConfiguration(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    newUserConfigurationDocument = newConfiguration
                )
                    .handleNoContent(gson)
            }
        }
}