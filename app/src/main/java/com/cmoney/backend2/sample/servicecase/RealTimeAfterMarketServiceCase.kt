package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class RealTimeAfterMarketServiceCase: ServiceCase {

    private val web by inject<RealTimeAfterMarketWeb>()

    override suspend fun testAll() {
        web.getCommList(listOf("1")).logResponse(TAG)
        web.getNewTickInfo(
            commKeys = listOf("2330"),
            statusCodes = listOf(0),
            isSimplified = false
        ).logResponse(TAG)
        web.getSingleStockLongNewTick(
            commKey = "2330",
            statusCode = "0"
        ).logResponse(TAG)
        web.getMarketNewTick(commKey = "TWA00", statusCode = "0").logResponse(TAG)
        web.getInternationalNewTick("TXF1", "0").logResponse(TAG)
        web.getDtno(4210983, "", "", "", 0).logResponse(TAG)
        web.getAfterHoursTime().logResponse(TAG)
        web.searchStock("電子").logResponse(TAG)
        web.searchStock("2330").logResponse(TAG)
        web.searchUsStock("Apple").logResponse(TAG)
        web.getForeignExchangeTicks(listOf("SUSDTWD" to 0)).logResponse(TAG)
        web.getStockDealDetail(
            commKey = "2330",
            perReturnCode = 0,
            timeCode = 0
        )
        web.getIsInTradeDay().logResponse(TAG)
        web.getStockSinIndex("TWB12").logResponse(TAG)
    }

    companion object {
        private const val TAG = "RealTime"
    }
}