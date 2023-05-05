package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.additioninformationrevisit.di.BACKEND_ADDITION_INFORMATION_REVISIT_SERVICE
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb
import com.cmoney.backend2.additioninformationrevisit.service.ServicePath
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.sample.extension.logResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

/**
 * Additional information revisit test case
 *
 * @param hasSignal 是否有要測試[AdditionalInformationRevisitWeb.getSignal]，用於調整測試機的service name
 */
class AdditionalInformationRevisitTestCase(hasSignal: Boolean) : ServiceCase {

    private val web by lazy {
        get<AdditionalInformationRevisitWeb>(BACKEND_ADDITION_INFORMATION_REVISIT_SERVICE) {
            parametersOf(servicePath)
        }
    }
    private val gson by inject<Gson>(BACKEND2_GSON)
    private val globalBackend2Manager by inject<GlobalBackend2Manager>()
    private val servicePath = if (hasSignal) {
        ServicePath().copy(signal = "AdditionInformationRevisit_V2")
    } else {
        ServicePath()
    }

    override suspend fun testAll() {
        test()
        // 測試後續處理功能
        testHasProcessStep()
        // 測試前一次交易資料
        testPreviousData()
    }

    private suspend fun test() {
        web.getAll(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.all,
            columns = listOf("標的", "商品名稱"),
            typeName = "StockCommodity",
            processSteps = emptyList()
        )
            .logResponse(TAG)
        val commKeys = listOf("2330", "0050")
        web.getTarget(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.target,
            typeName = "StockCalculation",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(commKeys),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        web.getMultiple(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.multiple,
            typeName = "CandlestickChartTick<StockCommodity,StockTick>",
            columns = listOf("傳輸序號", "標的", "收盤價"),
            keyNamePath = listOf("Key"),
            value = gson.toJson(CandleChartRequestValue(commodity = "2330", minuteInterval = 1)),
            processSteps = emptyList()
        )
            .logResponse(TAG)
        web.getOtherQuery(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.otherQuery,
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
        web.getSignal(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
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
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.all,
            columns = listOf("標的", "商品名稱"),
            typeName = "StockCommodity",
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
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.target,
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
        web.getMultiple(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.multiple,
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
        web.getOtherQuery(
            domain = globalBackend2Manager.getGlobalDomainUrl(),
            serviceParam = servicePath.otherQuery,
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
        web.getPreviousAll(
            columns = listOf("標的", "商品名稱"),
            typeName = "USAStockCommodity",
            processSteps = emptyList()
        ).logResponse(TAG)

        val commKeys = listOf("AAPL", "AMZN")
        web.getPreviousTarget(
            typeName = "USAStockCalculation",
            columns = listOf("傳輸序號", "標的", "即時成交價"),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(commKeys),
            processSteps = emptyList()
        ).logResponse(TAG)

        web.getPreviousMultiple(
            typeName = "CandlestickChartTick<USAStockCommodity, USAStockTick>",
            columns = listOf("傳輸序號", "標的", "收盤價"),
            keyNamePath = listOf("Key"),
            value = gson.toJson(CandleChartRequestValue(commodity = "AAPL", minuteInterval = 1)),
            processSteps = emptyList()
        ).logResponse(TAG)

        web.getPreviousOtherQuery(
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
        private const val TAG = "AIRTestCase"
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
