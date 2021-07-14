package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class RealTimeAfterMarketServiceCase: ServiceCase {

    private val realTimeAfterMarketServiceImpl by inject<RealTimeAfterMarketWeb>()

    override suspend fun testAll() {
        realTimeAfterMarketServiceImpl.getCommList(listOf("1")).logResponse(TAG)
        realTimeAfterMarketServiceImpl.getNewTickInfo(
            commKeys = listOf("2330"),
            statusCodes = listOf(0),
            isSimplified = false
        ).logResponse(TAG)
        realTimeAfterMarketServiceImpl.getSingleStockLongNewTick(
            commKey = "2330",
            statusCode = "0"
        ).logResponse(TAG)
        realTimeAfterMarketServiceImpl.getMarketNewTick(commKey = "TWA00", statusCode = "0").logResponse(TAG)
        realTimeAfterMarketServiceImpl.getInternationalNewTick("TXF1", "0").logResponse(TAG)
        realTimeAfterMarketServiceImpl.getDtno(4210983, "", "", "", 0).logResponse(TAG)
        realTimeAfterMarketServiceImpl.getAfterHoursTime().logResponse(TAG)
        realTimeAfterMarketServiceImpl.searchStock("電子").logResponse(TAG)
        realTimeAfterMarketServiceImpl.searchStock("2330").logResponse(TAG)
        realTimeAfterMarketServiceImpl.searchUsStock("Apple").logResponse(TAG)
        realTimeAfterMarketServiceImpl.getForeignExchangeTicks(listOf("SUSDTWD" to 0)).logResponse(TAG)
        realTimeAfterMarketServiceImpl.getStockDealDetail(
            commKey = "2330",
            perReturnCode = 0,
            timeCode = 0
        )
        realTimeAfterMarketServiceImpl.getIsInTradeDay().logResponse(TAG)
    }

    companion object {
        private const val TAG = "RealTime"
    }
}