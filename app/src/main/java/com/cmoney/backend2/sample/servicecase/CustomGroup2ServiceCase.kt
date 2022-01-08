package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.CustomGroup2Web
import com.cmoney.backend2.customgroup2.service.api.data.DocMarketType
import com.cmoney.backend2.customgroup2.service.api.data.MarketTypeV2
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CustomGroup2ServiceCase : ServiceCase {

    private val customGroup2Web by inject<CustomGroup2Web>()

    override suspend fun testAll() {
        customGroup2Web.searchStocksV2("11", Language.zhTw())
            .logResponse(TAG)
        customGroup2Web.searchStocksV2("11", listOf(Language.zhTw()))
            .logResponse(TAG)
        // 搜尋上市權證
        customGroup2Web.searchStocksByMarketTypesV2(
            "1269", Language.zhTw(), MarketTypeV2.TseWarrant.getAll()
        ).logResponse(TAG)
        // 搜尋上櫃權證
        customGroup2Web.searchStocksByMarketTypesV2(
            "12", Language.zhTw(), MarketTypeV2.OtcWarrant.getAll()
        ).logResponse(TAG)
        // 複數類型搜尋
        val marketTypes = mutableListOf<MarketTypeV2>().apply {
            addAll(MarketTypeV2.Tse.getAll())
            addAll(MarketTypeV2.Otc.getAll())
            addAll(MarketTypeV2.Emerging.getAll())
            addAll(MarketTypeV2.TseWarrant.getAll())
            addAll(MarketTypeV2.OtcWarrant.getAll())
        }
        customGroup2Web.searchStocksByMarketTypesV2(
            "12", Language.zhTw(), marketTypes
        ).logResponse(TAG)
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