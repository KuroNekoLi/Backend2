package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.additioninformationrevisit.service.ServicePath
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.sample.extension.logResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.koin.core.get
import org.koin.core.inject

/**
 * Additional information revisit test case
 *
 * @param hasSignal 是否有要測試[AdditionalInformationRevisitWeb.getSignal]，用於調整測試機的service name
 */
class AdditionalInformationRevisitTestCase(hasSignal: Boolean) : ServiceCase {

    private val web by lazy {
        get<AdditionalInformationRevisitWeb>()
    }
    private val gson by inject<Gson>(BACKEND2_GSON)
    private val setting by inject<Setting>(BACKEND2_SETTING)
    private val servicePath = if (hasSignal) {
        ServicePath().copy(signal = "AdditionInformationRevisit_V2")
    } else {
        ServicePath()
    }

    override suspend fun testAll() {
        test()
        // 測試後續處理功能
        testHasProcessStep()
    }

    private suspend fun test() {
        web.getAll(
            domain = setting.domainUrl,
            serviceParam = servicePath.all,
            columns = TEST_COLUMNS,
            typeName = TEST_TYPE_NAME,
            processSteps = emptyList()
        )
            .logResponse(TAG)
        val commKeys = listOf("2330", "0050")
        web.getTarget(
            domain = setting.domainUrl,
            serviceParam = servicePath.target,
            typeName = TEST_TYPE_NAME,
            columns = TEST_COLUMNS,
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(commKeys),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        web.getMultiple(
            domain = setting.domainUrl,
            serviceParam = servicePath.multiple,
            typeName = "CandlestickChart<StockCommodity>",
            columns = TEST_MULTIPLE_CANDLE_COLUMNS,
            keyNamePath = listOf("Key"),
            value = gson.toJson(CandleChartRequestValue(commodity = "2330", minuteInterval = 1)),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        web.getOtherQuery(
            domain = setting.domainUrl,
            serviceParam = servicePath.otherQuery,
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
            processSteps = emptyList()
        )
            .logResponse(TAG)
        web.getSignal(
            domain = setting.domainUrl,
            serviceParam = servicePath.signal,
            channels = listOf(
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
            domain = setting.domainUrl,
            serviceParam = servicePath.all,
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
            domain = setting.domainUrl,
            serviceParam = servicePath.target,
            typeName = TEST_TYPE_NAME,
            columns = TEST_COLUMNS,
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
        web.getMultiple(
            domain = setting.domainUrl,
            serviceParam = servicePath.multiple,
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
            domain = setting.domainUrl,
            serviceParam = servicePath.otherQuery,
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
