package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.chipk.service.ChipKWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class ChipKServiceCase : ServiceCase {
    private val chipKWeb by inject<ChipKWeb>()

    override suspend fun testAll() {
        chipKWeb.apply {
            getData("2330", 1).logResponse("TAG")
            getIndexKData("2330", 1).logResponse("TAG")
            getChipKData(1, "_").logResponse("TAG")
            getOfficialStockPickData(0, 2).logResponse("TAG")
            getOfficialStockPickTitle( 2).logResponse("TAG")
        }
    }
}