package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.customgroup2.service.CustomGroup2Web
import com.cmoney.backend2.customgroup2.service.api.data.Language
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class CustomGroup2ServiceCase: ServiceCase {

    private val customGroup2Web by inject<CustomGroup2Web>()

    override suspend fun testAll() {
        customGroup2Web.searchStocks("11", Language.TRADITIONAL_CHINESE)
            .logResponse(TAG)
        customGroup2Web.searchStocksByMarketTypes("c", Language.ENGLISH, marketTypes = listOf(MarketType.USA))
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "CustomGroup2"
    }
}