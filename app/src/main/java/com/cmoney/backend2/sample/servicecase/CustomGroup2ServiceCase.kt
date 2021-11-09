package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.CustomGroup2Web
import com.cmoney.backend2.customgroup2.service.api.data.DocMarketType
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CustomGroup2ServiceCase : ServiceCase {

    private val customGroup2Web by inject<CustomGroup2Web>()

    override suspend fun testAll() {
        customGroup2Web.searchStocks("11", Language.zhTw())
            .logResponse(TAG)
        customGroup2Web.searchStocks("11", listOf(Language.zhTw()))
            .logResponse(TAG)
        customGroup2Web.searchStocksByMarketTypes(
            "c", Language.enUs(1.0), listOf(MarketType.UsaStock())
        )
            .logResponse(TAG)
        customGroup2Web.searchStocksByMarketTypes(
            "c",
            listOf(
                Language.zhTw(0.9),
                Language.enUs(1.0)
            ),
            listOf(MarketType.UsaStock())
        )
            .logResponse(TAG)

        val marketType = DocMarketType.Stock
        val nonOrderedCustomGroups = customGroup2Web.getCustomGroup(marketType).also { result ->
            result.logResponse(TAG)
        }.getOrNull() ?: return
        if (nonOrderedCustomGroups.isNotEmpty()) {
            customGroup2Web.getCustomGroup(nonOrderedCustomGroups.first().id)
                .logResponse(TAG)
        }
        val userConfiguration = customGroup2Web.getUserConfiguration().also { result ->
            result.logResponse(TAG)
        }.getOrNull() ?: return
        val orderedCustomGroups = nonOrderedCustomGroups.sortedBy { customGroup ->
            userConfiguration.customGroupOrders?.get(customGroup.id) ?: Int.MAX_VALUE
        }
        val newGroup = customGroup2Web.createCustomGroup(
            "測試群組",
            marketType
        ).also { result ->
            result.logResponse(TAG)
        }.getOrNull() ?: return
        customGroup2Web.updateCustomGroup(
            newGroup.copy(name = "測試群組2")
        ).logResponse(TAG)
        customGroup2Web.getCustomGroup(newGroup.id).logResponse(TAG)
        customGroup2Web.deleteCustomGroup(newGroup.id).logResponse(TAG)
        val reveredCustomGroups = orderedCustomGroups.reversed()
        customGroup2Web.updateConfiguration(
            reveredCustomGroups
        ).logResponse(TAG)
        customGroup2Web.getUserConfiguration().logResponse(TAG)
        customGroup2Web.updateConfiguration(
            orderedCustomGroups
        ).logResponse(TAG)
        customGroup2Web.getUserConfiguration().logResponse(TAG)
    }

    companion object {
        private const val TAG = "CustomGroup2"
    }
}