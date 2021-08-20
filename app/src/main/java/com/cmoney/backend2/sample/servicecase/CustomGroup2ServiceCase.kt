package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.CustomGroup2Web
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

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
    }

    companion object {
        private const val TAG = "CustomGroup2"
    }
}