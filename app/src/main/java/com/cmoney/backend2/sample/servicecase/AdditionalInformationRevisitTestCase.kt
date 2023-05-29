package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.additioninformationrevisit.di.ADDITIONAL_INFORMATION_REVISIT_TW_WEB
import com.cmoney.backend2.additioninformationrevisit.di.ADDITIONAL_INFORMATION_REVISIT_US_WEB
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.sample.extension.logResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.koin.core.component.inject

/**
 * 附加資訊測試案例
 */
class AdditionalInformationRevisitTestCase : ServiceCase {

    private val twWeb by inject<AdditionalInformationRevisitWeb>(ADDITIONAL_INFORMATION_REVISIT_TW_WEB)
    private val usWeb by inject<AdditionalInformationRevisitWeb>(ADDITIONAL_INFORMATION_REVISIT_US_WEB)
    private val gson by inject<Gson>(BACKEND2_GSON)

    override suspend fun testAll() {
        test()
        // 測試後續處理功能
        testProcessStep()
        // 測試前一次交易資料
        testPreviousData()
    }

    private suspend fun test() {
        twWeb.getAll(
            columns = listOf("標的", "商品名稱"),
            typeName = "StockCommodity",
            processSteps = emptyList()
        ).logResponse(TAG)
        val commKeys = listOf("2330", "0050")
        twWeb.getTarget(
            typeName = "StockCalculation",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(commKeys),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        twWeb.getMultiple(
            typeName = "CandlestickChartTick<StockCommodity,StockTick>",
            columns = listOf("傳輸序號", "標的", "收盤價"),
            keyNamePath = listOf("Key"),
            value = gson.toJson(CandleChartRequestValue(commodity = "2330", minuteInterval = 1)),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        twWeb.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest<StockTick>",
            responseType = "IEnumerable<StockTick>",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            value = gson.toJson(
                StockTickRequestValue(
                    commKey = "2330",
                    startSeqNo = Int.MAX_VALUE,
                    requiredQuantity = 10
                )
            ),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        twWeb.getSignal(
            channels = listOf(
                "2101582"
            )
        ).logResponse(TAG)
    }

    private suspend fun testProcessStep() {
        twWeb.getAll(
            columns = listOf("標的", "商品名稱"),
            typeName = "StockCommodity",
            processSteps = listOf(
                ProcessStep(
                    type = "TakeCount",
                    json = "{\"Count\":10}"
                ),
                ProcessStep(
                    type = "AscOrder",
                    json = "{\"TargetPropertyNamePath\":[\"TradeDate\"]}"
                )
            )
        )
            .logResponse(TAG)
        val commKeys = listOf("2330", "0050", "2344", "3008")
        twWeb.getTarget(
            typeName = "StockCalculation",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(commKeys),
            processSteps = listOf(
                ProcessStep(
                    type = "TakeCount",
                    json = "{\"Count\":3}"
                ),
                ProcessStep(
                    type = "AscOrder",
                    json = "{\"TargetPropertyNamePath\":[\"High\"]}"
                )
            )
        )
            .logResponse(TAG)
        twWeb.getMultiple(
            typeName = "CandlestickChartTick<StockCommodity,StockTick>",
            columns = listOf("傳輸序號", "標的", "收盤價"),
            keyNamePath = listOf("Key"),
            value = gson.toJson(CandleChartRequestValue(commodity = "2330", minuteInterval = 1)),
            processSteps = listOf(
                ProcessStep(
                    type = "TakeCount",
                    json = "{\"Count\":5}"
                ),
                ProcessStep(
                    type = "DescOrder",
                    json = "{\"TargetPropertyNamePath\":[\"Index\"]}"
                )
            )
        )
            .logResponse(TAG)
        twWeb.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest<StockTick>",
            responseType = "IEnumerable<StockTick>",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            value = gson.toJson(
                StockTickRequestValue(
                    commKey = "2330",
                    startSeqNo = Int.MAX_VALUE,
                    requiredQuantity = 10
                )
            ),
            processSteps = listOf(
                ProcessStep(
                    type = "TakeCount",
                    json = "{\"Count\":5}"
                ),
                ProcessStep(
                    type = "AscOrder",
                    json = "{\"TargetPropertyNamePath\":[\"SeqNo\"]}"
                )
            )
        )
            .logResponse(TAG)
    }

    private suspend fun testPreviousData() {
        // 目前沒有相關的GetPreviousAll類型
//        web.getPreviousAll(
//            columns = listOf(""),
//            typeName = "",
//            processSteps = emptyList()
//        ).logResponse(TAG)

        val commKeys = listOf("AAPL", "AMZN")
        usWeb.getPreviousTarget(
            typeName = "USAStockCalculation",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(commKeys),
            processSteps = emptyList()
        ).logResponse(TAG)

        usWeb.getPreviousMultiple(
            typeName = "CandlestickChartTick<USAStockCommodity, USAStockTick>",
            columns = listOf("傳輸序號", "標的", "收盤價"),
            keyNamePath = listOf("Key"),
            value = gson.toJson(CandleChartRequestValue(commodity = "AAPL", minuteInterval = 1)),
            processSteps = emptyList()
        ).logResponse(TAG)

        usWeb.getPreviousOtherQuery(
            requestType = "SectionTransactionDetailsRequest<USAStockTick>",
            responseType = "IEnumerable<USAStockTick>",
            columns = TEST_COLUMNS,
            value = gson.toJson(
                StockTickRequestValue(
                    commKey = "AAPL",
                    startSeqNo = Int.MAX_VALUE,
                    requiredQuantity = 10
                )
            ),
            processSteps = emptyList()
        ).logResponse(TAG)
    }

    companion object {
        private const val TAG = "AdditionalInformationRevisitTestCase"
        private val TEST_COLUMNS = listOf("傳輸序號", "標的", "即時成交價")
    }

    private data class StockTickRequestValue(
        @SerializedName("CommKey")
        val commKey: String,
        @SerializedName("StartSeqNo")
        val startSeqNo: Int,
        @SerializedName("RequiredQuantity")
        val requiredQuantity: Int
    )

    private data class CandleChartRequestValue(
        @SerializedName("Commodity")
        val commodity: String,
        @SerializedName("MinuteInterval")
        val minuteInterval: Int
    )
}
