package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.dtno.service.DtnoWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class DtnoServiceCase : ServiceCase {

    private val dtnoWeb by inject<DtnoWeb>()

    override suspend fun testAll() {
        dtnoWeb.getKLineData("2330", 1, 240)
            .logResponse(TAG)

        dtnoWeb.getLatestBasicInfo(listOf("TSM", "CHT"), 0)
            .logResponse(TAG)
    }

    companion object {
        private val TAG = "Dtno"
    }
}