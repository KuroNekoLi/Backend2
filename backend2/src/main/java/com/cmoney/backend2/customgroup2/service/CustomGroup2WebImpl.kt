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
import retrofit2.Response

class CustomGroup2WebImpl(
    private val setting: Setting,
    private val gson: Gson,
    private val service: CustomGroup2Service,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : CustomGroup2Web {

    override suspend fun searchStocks(
        keyword: String,
        languages: List<Language>
    ): Result<List<Stock>> =
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

    override suspend fun getCustomGroup(marketType: DocMarketType): Result<List<CustomGroup>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    filters = mapOf(FILTERS_KEY to "${DocMarketType.KEY}:${marketType.value}")
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
                val document = service.getCustomGroup(
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
                val response = service.updateCustomGroup(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    id = newGroup.id,
                    newGroup = newDocument
                )
                response.handleCustomGroupNoContent()
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
                    .handleHttpStatusCode(gson) { _, responseBody ->
                        responseBody?.string()
                    } ?: throw IllegalStateException("id return is null")
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
                    .handleCustomGroupNoContent()
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
                    .handleCustomGroupNoContent()
            }
        }

    private fun Response<Void>.handleCustomGroupNoContent() {
        return when (this.code()) {
            200 -> {
            }
            else -> {
                this.handleNoContent(gson)
            }
        }
    }

    companion object {
        private const val FILTERS_KEY = "filters"
    }
}