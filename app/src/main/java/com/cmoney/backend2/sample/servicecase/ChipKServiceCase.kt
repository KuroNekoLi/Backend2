package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.chipk.service.ChipKWeb
import com.cmoney.backend2.chipk.service.api.internationalkchart.ProductType
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class ChipKServiceCase : ServiceCase {
    private val chipKWeb by inject<ChipKWeb>()

    override suspend fun testAll() {
        chipKWeb.apply {
            getData("2330", 1).logResponse("TAG")
            getIndexKData("2330", 1).logResponse("TAG")
            getChipKData(1, "_").logResponse("TAG")
            getOfficialStockPickData(0, 2).logResponse("TAG")
            getOfficialStockPickTitle( 2).logResponse("TAG")

            //服務6-6. 要求大盤外資的資料(TWA00)
            getIndexForeignInvestment(20).logResponse("TAG_6-6")

            //服務6-7. 要求大盤主力的資料(TWA00)
            getIndexMain(20).logResponse("TAG_6-7")

            //服務6-8. 要求大盤資券資料(TWA00)
            getIndexFunded(20).logResponse("TAG_6-8")

            // 服務6-9. 要求國際盤後資料
            getInternationalKChart("#N225", ProductType.InternationalIndex).logResponse("TAG_6-9")

            //服務6-10. 取得加權指數融資維持率
            getCreditRate().logResponse("TAG_6-10")

            //服務6-11. 取得指數技術圖
            getIndexCalculateRate("TWA00", 1).logResponse("TAG_6-11")

            //期貨盤後資訊 服務 - 官股、融資
            getFutureDayTradeIndexAnalysis().logResponse("TAG")
        }
    }
}