package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.tickdata.service.TickDataWeb
import org.koin.core.inject

class TickDataServiceCase : ServiceCase {

    private val tickDataWeb by inject<TickDataWeb>()

    override suspend fun testAll() {
        tickDataWeb.getKChartData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        ).logResponse(TAG)

        tickDataWeb.getMAData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        ).logResponse(TAG)

        tickDataWeb.getMultipleMovingAverage(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5,
            dataPoints = listOf(1, 5, 30, 60)
        )
    }

    companion object {
        private const val TAG = "TickData"
    }
}