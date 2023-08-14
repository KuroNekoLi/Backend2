package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.extension.asRequestHeader
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.api.data.CustomGroup
import com.cmoney.backend2.customgroup2.service.api.data.DocMarketType
import com.cmoney.backend2.customgroup2.service.api.data.Document
import com.cmoney.backend2.customgroup2.service.api.data.MarketTypeV2
import com.cmoney.backend2.customgroup2.service.api.data.RequestMarketType
import com.cmoney.backend2.customgroup2.service.api.data.StockV2
import com.cmoney.backend2.customgroup2.service.api.data.UserConfiguration
import com.cmoney.backend2.customgroup2.service.api.data.UserConfigurationDocument
import com.cmoney.backend2.customgroup2.service.api.searchstocks.SearchStocksRequestBody
import com.cmoney.backend2.customgroup2.service.api.searchstocksbymarkettype.SearchStocksByMarketTypeRequestBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class CustomGroup2WebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: CustomGroup2Service,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : CustomGroup2Web {
    override suspend fun searchStocksV2(
        keyword: String,
        language: Language,
        domain: String,
        url: String
    ): Result<List<StockV2>> {
        return searchStocksV2(
            keyword = keyword,
            languages = listOf(language),
            domain = domain,
            url = url
        )
    }

    override suspend fun searchStocksV2(
        keyword: String,
        languages: List<Language>,
        domain: String,
        url: String
    ): Result<List<StockV2>> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = SearchStocksRequestBody(keyword = keyword)
            service.searchStocks(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                language = languages.asRequestHeader(),
                requestBody = requestBody
            ).checkResponseBody(gson)
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

    override suspend fun searchStocksByMarketTypesV2(
        keyword: String,
        language: Language,
        marketTypes: List<MarketTypeV2>,
        domain: String,
        url: String
    ): Result<List<StockV2>> {
        return searchStocksByMarketTypesV2(
            keyword = keyword,
            languages = listOf(language),
            marketTypes = marketTypes,
            domain = domain,
            url = url
        )
    }

    override suspend fun searchStocksByMarketTypesV2(
        keyword: String,
        languages: List<Language>,
        marketTypes: List<MarketTypeV2>,
        domain: String,
        url: String
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
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                language = languages.asRequestHeader(),
                requestBody = requestBody
            ).checkResponseBody(gson)
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

    override suspend fun getCustomGroup(
        marketType: DocMarketType,
        domain: String,
        url: String
    ): Result<List<CustomGroup>> = withContext(dispatcher.io()) {
        runCatching {
            val docTypeCondition = "type:StockGroup"
            val marketTypeCondition = "${DocMarketType.KEY}:${marketType.value}"
            val filters = listOf(docTypeCondition, marketTypeCondition)
            service.getCustomGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                filters = filters.joinToString(",")
            ).checkResponseBody(gson)
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

    override suspend fun getCustomGroup(
        id: String,
        domain: String,
        url: String
    ): Result<CustomGroup> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val document = service.getCustomGroupBy(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
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

    override suspend fun updateCustomGroup(
        newGroup: CustomGroup,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            val newDocument = Document(
                id = newGroup.id,
                displayName = newGroup.name,
                marketType = newGroup.marketType?.value,
                stocks = newGroup.stocks
            )
            service.updateCustomGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                newGroup = newDocument
            ).handleNoContent(gson)
        }
    }

    override suspend fun createCustomGroup(
        displayName: String,
        marketType: DocMarketType,
        domain: String,
        url: String
    ): Result<CustomGroup> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val baseDocument = Document(
                    displayName = displayName,
                    marketType = marketType.value,
                    stocks = emptyList()
                )
                val groupId = service.createCustomGroup(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    baseDocument = baseDocument
                ).checkResponseBody(gson)
                    .id ?: throw IllegalStateException("id return is null")
                CustomGroup(
                    id = groupId,
                    name = displayName,
                    marketType = marketType,
                    stocks = emptyList()
                )
            }
        }

    override suspend fun deleteCustomGroup(
        id: String,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.deleteCustomGroup(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(gson)
        }
    }

    override suspend fun getUserConfiguration(
        domain: String,
        url: String
    ): Result<UserConfiguration> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val userConfigurationDocument = service.getUserConfiguration(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
                UserConfiguration(
                    customGroupOrders = userConfigurationDocument.documentOrders
                )
            }
        }

    override suspend fun updateConfiguration(
        customGroups: List<CustomGroup>,
        domain: String,
        url: String
    ): Result<Unit> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val newConfiguration = UserConfigurationDocument(
                    documentOrders = customGroups.mapIndexed { index, customGroup ->
                        customGroup.id to (index + 1)
                    }.toMap()
                )
                service.updateUserConfiguration(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    newUserConfigurationDocument = newConfiguration
                ).handleNoContent(gson)
            }
        }
}