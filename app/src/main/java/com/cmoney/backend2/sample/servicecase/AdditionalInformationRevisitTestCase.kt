package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.additioninformationrevisit.di.BACKEND_ADDITION_INFORMATION_REVISIT_SERVICE
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.additioninformationrevisit.service.ServicePath
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.sample.extension.logResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class AdditionalInformationRevisitTestCase(private val isSignal: Boolean) : ServiceCase {

    private val web by lazy {
        if (isSignal) {
            get<AdditionalInformationRevisitWeb>(BACKEND_ADDITION_INFORMATION_REVISIT_SERVICE) {
                parametersOf(ServicePath().copy(signal = "AdditionInformationRevisit_V2"))
            }
        } else {
            get<AdditionalInformationRevisitWeb>()
        }
    }
    private val gson by inject<Gson>(BACKEND2_GSON)

    override suspend fun testAll() {
        test()
        // 測試後續處理功能
        testHasProcessStep()
    }

    private suspend fun test() {
        web.getAll(TEST_COLUMNS, TEST_TYPE_NAME, emptyList())
            .logResponse(TAG)
        val commKeys = listOf("2330", "0050")
        web.getTarget(
            TEST_TYPE_NAME,
            TEST_COLUMNS,
            listOf("Commodity", "CommKey"),
            gson.toJson(commKeys),
            emptyList()
        )
            .logResponse(TAG)
        web.getMultiple(
            "CandlestickChart<StockCommodity>",
            TEST_MULTIPLE_CANDLE_COLUMNS,
            listOf("Key"),
            gson.toJson(CandleChartRequestValue(commodity = "2330", minuteInterval = 1)),
            emptyList()
        )
            .logResponse(TAG)
        web.getOtherQuery(
            "SectionTransactionDetailsRequest<StockTick>",
            "IEnumerable<StockTick>",
            TEST_COLUMNS,
            gson.toJson(
                StockTickRequestValue(
                    commKey = "2330",
                    startSeqNo = Int.MAX_VALUE,
                    requiredQuantity = 10
                )
            ),
            emptyList()
        )
            .logResponse(TAG)
        web.getSignal(
            listOf(
                // 台股上市櫃(不含ETF)
                "1175865",
                // 可現股當沖標的(不含ETF)
                "13335330"
            )
        )
            .logResponse(TAG)
    }

    private suspend fun testHasProcessStep() {
        web.getAll(
            columns = TEST_COLUMNS,
            typeName = TEST_TYPE_NAME,
            processSteps = listOf(
                ProcessStep(
                    type = "TakeCount",
                    json = "{\"Count\":10}"
                ),
                ProcessStep(
                    type = "AscOrder",
                    json = "{\"TargetPropertyNamePath\":[\"High\"]}"
                )
            )
        )
            .logResponse(TAG)
        val commKeys = listOf("2330", "0050", "2344", "3008")
        web.getTarget(
            TEST_TYPE_NAME,
            TEST_COLUMNS,
            listOf("Commodity", "CommKey"),
            gson.toJson(commKeys),
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
        web.getMultiple(
            typeName = "CandlestickChart<StockCommodity>",
            columns = TEST_MULTIPLE_CANDLE_COLUMNS,
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
        web.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest<StockTick>",
            responseType = "IEnumerable<StockTick>",
            columns = TEST_COLUMNS,
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

    companion object {
        private const val TAG = "AIRTestCase"
        private const val TEST_TYPE_NAME = "StockCalculation"
        private val TEST_COLUMNS = listOf("傳輸序號", "標的", "即時成交價")
        private val TEST_MULTIPLE_CANDLE_COLUMNS = listOf("傳輸序號", "標的", "收盤價")
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
