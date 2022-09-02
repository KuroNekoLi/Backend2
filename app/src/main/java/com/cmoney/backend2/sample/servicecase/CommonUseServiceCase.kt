package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.commonuse.service.CommonUseWeb
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CommonUseServiceCase : ServiceCase {

    private val web by inject<CommonUseWeb>()

    override suspend fun testAll() {
        web.getRemoteConfigLabel()
            .logResponse(TAG)

        web.updateInvestmentPreference(investmentPreferenceType = InvestmentPreferenceType.All)
            .logResponse(TAG)

        web.getInvestmentPreferences()
            .logResponse(TAG)

        getAllCommodityHistoryEvent(commodityIds = listOf("5880"))
    }

    private suspend fun getAllCommodityHistoryEvent(commodityIds: List<String>) {
        var hasNextPage = true
        var endCursor: String? = null
        while (hasNextPage) {
            val result = web.getCommodityHistoryEvent(
                commodityIds = commodityIds,
                endCursor = endCursor
            )
                .also { response ->
                    response.logResponse(TAG)
                }
                .getOrThrow()
            hasNextPage = result.pageInfo?.hasNextPage ?: false
            endCursor = result.pageInfo?.endCursor
        }
    }

    companion object {
        private const val TAG = "CommonUse"
    }
}
