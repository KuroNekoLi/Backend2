package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.base.model.response.error.ISuccess
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalNewTicks
import com.cmoney.backend2.realtimeaftermarket.service.api.getafterhourstime.AfterHoursTimeWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.GetCommListResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.Product
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.ProductInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail.StockDealDetailWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks.GetForeignExchangeTickResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday.GetIsInTradeDayResponseBodyWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.NewTickInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.TickInfoSet
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.ChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.GroupedPriceVolumeData
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.InvestorChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.SingleStockNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex.GetStocksInIndexResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex.GetStocksInIndexResponseBodyWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex.Stock
import com.cmoney.backend2.realtimeaftermarket.service.api.searchstock.ResultEntry
import com.cmoney.backend2.realtimeaftermarket.service.api.searchustock.UsResultEntry
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.text.ParseException
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class RealTimeAfterMarketWebImplTest {

    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: RealTimeAfterMarketService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: RealTimeAfterMarketWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = RealTimeAfterMarketWebImpl(
            manager = manager,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getRealtimeAfterMarketSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getCommList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val mockResponse = GetCommListResponseBody(
            products = emptyList(),
            responseCode = 1
        )
        coEvery {
            service.getCommList(
                url = capture(urlSlot),
                authorization = any(),
                areaIds = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(mockResponse)
        web.getCommList(listOf("1"))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCommList_success() = testScope.runTest {
        val mockResponse = GetCommListResponseBody(
            products = listOf(
                Product(
                    1,
                    listOf(
                        ProductInfo(
                            commKey = "MYMF1",
                            name = "小道瓊期貨",
                            countryCode = 840,
                            isShowPreviousClosePr = false
                        )
                    )
                )
            ),
            responseCode = 1
        )
        coEvery {
            service.getCommList(
                url = any(),
                authorization = any(),
                areaIds = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(mockResponse)
        val result = web.getCommList(listOf("1"))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.responseCode).isEqualTo(1)
        Truth.assertThat(data.products?.size).isEqualTo(1)
        val product = data.products?.firstOrNull()!!
        Truth.assertThat(product.areaId).isEqualTo(1)
        Truth.assertThat(product.productInfos?.size).isEqualTo(1)
        val productInfo = product.productInfos!!.first()
        Truth.assertThat(productInfo.commKey).isEqualTo("MYMF1")
        Truth.assertThat(productInfo.name).isEqualTo("小道瓊期貨")
        Truth.assertThat(productInfo.countryCode).isEqualTo(840)
        Truth.assertThat(productInfo.isShowPreviousClosePr).isFalse()
    }

    @Test
    fun `getNewTickInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val response = NewTickInfo(
            isMarketClosed = true,
            isSuccess = true,
            responseCode = 0,
            responseMsg = "",
            tickInfoSet = listOf()
        )
        coEvery {
            service.getNewTickInfo(
                url = capture(urlSlot),
                commKeys = any(),
                statusCodes = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        web.getNewTickInfo(listOf("1111"), listOf(1))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNewTickInfo_success() = testScope.runTest {
        val response = NewTickInfo(
            isMarketClosed = true,
            isSuccess = true,
            responseCode = 0,
            responseMsg = "",
            tickInfoSet = listOf(
                TickInfoSet(
                    commKey = "1111",
                    buyOrSell = 22,
                    refPrice = 23.0,
                    dealPrice = 12.2,
                    highestPrice = 98.2,
                    lowestPrice = 12.56,
                    limitDown = "down",
                    limitUp = "up",
                    openPrice = 658.2,
                    priceChange = 65.32,
                    quoteChange = 4.56,
                    singleVolume = 3564u,
                    totalVolume = 123789u,
                    tickTime = 654987L,
                    statusCode = 1,
                    packageDataType = 2,
                    investorStatus = 3
                )
            )
        )
        coEvery {
            service.getNewTickInfo(
                url = any(),
                commKeys = any(),
                statusCodes = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getNewTickInfo(listOf("1111"), listOf(1))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun getNewTickInfo_failure() = testScope.runTest {
        val response = NewTickInfo(
            isMarketClosed = true,
            isSuccess = false,
            responseCode = ISuccess.DEFAULT_ERROR_CODE,
            responseMsg = ISuccess.DEFAULT_ERROR_MESSAGE,
            tickInfoSet = emptyList()
        )
        coEvery {
            service.getNewTickInfo(
                url = any(),
                commKeys = any(),
                statusCodes = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getNewTickInfo(emptyList(), emptyList())
        checkServerException(result)
    }

    @Test
    fun `getSingleStockLongNewTick_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val response = SingleStockNewTick(
            askQty = null,
            averagePrice = null,
            bidQty = null,
            buyOrSell = null,
            buyPr1 = null,
            buyPr2 = null,
            buyPr3 = null,
            buyPr4 = null,
            buyPr5 = null,
            buyQty1 = null,
            buyQty2 = null,
            buyQty3 = null,
            buyQty4 = null,
            buyQty5 = null,
            ceilingPrice = null,
            chartData = listOf(),
            closePrice = null,
            commkey = null,
            groupedPriceVolumeData = listOf(),
            investorChartData = listOf(),
            isCloseMarket = null,
            isSuccess = null,
            limitDown = null,
            limitUp = null,
            lowestPrice = null,
            marketTime = null,
            openPrice = null,
            packageDataType = null,
            prevClose = null,
            priceChange = null,
            quoteChange = null,
            refPrice = null,
            responseCode = null,
            responseMsg = null,
            sellPr1 = null,
            sellPr2 = null,
            sellPr3 = null,
            sellPr4 = null,
            sellPr5 = null,
            sellQty1 = null,
            sellQty2 = null,
            sellQty3 = null,
            sellQty4 = null,
            sellQty5 = null,
            statusCode = null,
            tickQty = null,
            totalQty = null
        )
        coEvery {
            service.getSingleStockNewTick(
                url = capture(urlSlot),
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        web.getSingleStockLongNewTick("1111", "0")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSingleStockLongNewTick_success() = testScope.runTest {
        val response = SingleStockNewTick(
            askQty = 1,
            averagePrice = 12.2,
            bidQty = 2,
            buyOrSell = "buy",
            buyPr1 = 1.2,
            buyPr2 = 1.3,
            buyPr3 = 1.4,
            buyPr4 = 1.5,
            buyPr5 = 1.6,
            buyQty1 = 2,
            buyQty2 = 3,
            buyQty3 = 4,
            buyQty4 = 5,
            buyQty5 = 6,
            ceilingPrice = 456.3,
            chartData = listOf(
                ChartData(
                    averagePrice = 12.5,
                    closePrice = 46.5,
                    highestPrice = 98.5,
                    lowestPrice = 12.3,
                    openPrice = 45.5,
                    time = 6546L,
                    volumn = 9
                )
            ),
            closePrice = 65.5,
            commkey = "8888",
            groupedPriceVolumeData = listOf(
                GroupedPriceVolumeData(
                    askQty = 1,
                    bidQty = 5,
                    price = 65.2,
                    totalVolume = 69.2
                )
            ),
            investorChartData = listOf(
                InvestorChartData(
                    largeBuyQty = 2,
                    largeQty = 3,
                    largeSellQty = 4,
                    largeTotalQty = 5,
                    smallBuyQty = 6,
                    smallQty = 7,
                    smallSellQty = 8,
                    smallTotalQty = 9,
                    time = 9999L
                )
            ),
            isCloseMarket = true,
            isSuccess = true,
            limitDown = 23.5,
            limitUp = 65.2,
            lowestPrice = 54.5,
            marketTime = 1111L,
            openPrice = 6598.2,
            packageDataType = 8,
            prevClose = 65.25,
            priceChange = 56.6,
            quoteChange = 64.2,
            refPrice = 9863.25,
            responseCode = 0,
            responseMsg = "",
            sellPr1 = 1.1,
            sellPr2 = 2.2,
            sellPr3 = 3.3,
            sellPr4 = 4.4,
            sellPr5 = 5.5,
            sellQty1 = 9,
            sellQty2 = 8,
            sellQty3 = 7,
            sellQty4 = 6,
            sellQty5 = 5,
            tickQty = 99,
            totalQty = 11,
            statusCode = "0"
        )
        coEvery {
            service.getSingleStockNewTick(
                url = any(),
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getSingleStockLongNewTick("1111", "0")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun getSingleStockLongNewTick_failure() = testScope.runTest {
        val response = SingleStockNewTick(
            askQty = 0,
            averagePrice = 0.0,
            bidQty = 0,
            buyOrSell = "",
            buyPr1 = 0.0,
            buyPr2 = 0.0,
            buyPr3 = 0.0,
            buyPr4 = 0.0,
            buyPr5 = 0.0,
            buyQty1 = 0,
            buyQty2 = 0,
            buyQty3 = 0,
            buyQty4 = 0,
            buyQty5 = 0,
            ceilingPrice = 0.0,
            chartData = emptyList(),
            closePrice = 0.0,
            commkey = "",
            groupedPriceVolumeData = emptyList(),
            investorChartData = emptyList(),
            isCloseMarket = true,
            isSuccess = false,
            limitDown = 0.0,
            limitUp = 0.0,
            lowestPrice = 0.0,
            marketTime = 0L,
            openPrice = 0.0,
            packageDataType = 0,
            prevClose = 0.0,
            priceChange = 0.0,
            quoteChange = 0.0,
            refPrice = 0.0,
            responseCode = ISuccess.DEFAULT_ERROR_CODE,
            responseMsg = ISuccess.DEFAULT_ERROR_MESSAGE,
            sellPr1 = 0.0,
            sellPr2 = 0.0,
            sellPr3 = 0.0,
            sellPr4 = 0.0,
            sellPr5 = 0.0,
            sellQty1 = 0,
            sellQty2 = 0,
            sellQty3 = 0,
            sellQty4 = 0,
            sellQty5 = 0,
            tickQty = 0,
            totalQty = 0,
            statusCode = "0"
        )
        coEvery {
            service.getSingleStockNewTick(
                url = any(),
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getSingleStockLongNewTick("", "")
        checkServerException(result)
    }

    @Test
    fun `getMarketNewTick_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val response = MarketNewTick(
            isMarketClosed = null,
            tickInfoSet = listOf(),
            isSuccess = null,
            responseCode = null,
            responseMsg = null
        )
        coEvery {
            service.getMarketNewTick(
                url = capture(urlSlot),
                commkey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        web.getMarketNewTick("1111", "0")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMarketNewTick_success() = testScope.runTest {
        val response = MarketNewTick(
            isMarketClosed = true,
            tickInfoSet = listOf(
                com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.TickInfoSet(
                    commonKey = "1111",
                    ceilQty = 1,
                    downQty = 2,
                    floorQty = 3,
                    upQty = 4,
                    openPrice = 1.3,
                    highPrice = 1.5,
                    lowPrice = 1.0,
                    refPrice = 1.3,
                    salePrice = 1.4,
                    totalVolume = 12345L,
                    totalTurnover = 456799L,
                    singleTurnover = 987654L,
                    tickTime = 456123L,
                    quoteChange = 5.6,
                    statusCode = 0,
                    chartData = listOf(
                        MarketChartData(
                            closePrice = 1.5,
                            openPrice = 1.6,
                            highestPrice = 1.8,
                            lowestPrice = 1.7,
                            time = 65,
                            volume = 123L
                        )
                    ),
                    totalMarketBuySt = 123L,
                    totalMarketSellSt = 456L,
                    preTotalVolume = 789L
                )
            ),
            isSuccess = true,
            responseCode = 0,
            responseMsg = ""
        )
        coEvery {
            service.getMarketNewTick(
                url = any(),
                commkey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getMarketNewTick("1111", "0")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun getMarketNewTick_failure() = testScope.runTest {
        val response = MarketNewTick(
            isMarketClosed = false,
            tickInfoSet = emptyList(),
            isSuccess = false,
            responseCode = ISuccess.DEFAULT_ERROR_CODE,
            responseMsg = ISuccess.DEFAULT_ERROR_MESSAGE
        )
        coEvery {
            service.getMarketNewTick(
                url = any(),
                commkey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getMarketNewTick("", "")
        checkServerException(result)
    }

    @Test
    fun `getInternationalNewTick_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InternationalTrading.ashx"
        val urlSlot = slot<String>()
        val response = InternationalNewTicks(
            commKey = null,
            refPr = null,
            openPrice = null,
            ceilingPrice = null,
            lowestPrice = null,
            prevClose = null,
            salePr = null,
            marketTime = null,
            totalQty = null,
            priceChange = null,
            quoteChange = null,
            statusCode = null,
            chartData = listOf(),
            startTime = null,
            endTime = null,
            isSuccess = null,
            responseCode = null,
            responseMsg = null
        )
        coEvery {
            service.getInternationalNewTick(
                url = capture(urlSlot),
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        web.getInternationalNewTick("1111", "0")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getInternationalNewTick_success() = testScope.runTest {
        val response = InternationalNewTicks(
            commKey = "1111",
            refPr = "456",
            openPrice = 2.1,
            ceilingPrice = 2.2,
            lowestPrice = 2.0,
            prevClose = 1.8,
            salePr = 1.9,
            marketTime = 789L,
            totalQty = 456L,
            priceChange = 2.5,
            quoteChange = 2.6,
            statusCode = 0,
            chartData = listOf(
                InternationalChartData(
                    closePrice = 2.5,
                    openPrice = 2.5,
                    highestPrice = 3.0,
                    lowestPrice = 2.0,
                    time = 159687L,
                    volumn = 987321L
                )
            ),
            startTime = 789123L,
            endTime = 123456L,
            isSuccess = true,
            responseCode = 0,
            responseMsg = ""
        )
        coEvery {
            service.getInternationalNewTick(
                url = any(),
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getInternationalNewTick("1111", "0")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun getInternationalNewTick_failure() = testScope.runTest {
        val response = InternationalNewTicks(
            commKey = "1111",
            refPr = "",
            openPrice = 0.0,
            ceilingPrice = 0.0,
            lowestPrice = 0.0,
            prevClose = 0.0,
            salePr = 0.0,
            marketTime = 0L,
            totalQty = 0L,
            priceChange = 0.0,
            quoteChange = 0.0,
            statusCode = 0,
            chartData = emptyList(),
            startTime = 0L,
            endTime = 0L,
            isSuccess = false,
            responseCode = ISuccess.DEFAULT_ERROR_CODE,
            responseMsg = ISuccess.DEFAULT_ERROR_MESSAGE
        )
        coEvery {
            service.getInternationalNewTick(
                url = any(),
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getInternationalNewTick("", "")
        checkServerException(result)
    }

    @Test
    fun `getDtno_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/GetDtnoData.ashx"
        val urlSlot = slot<String>()
        val response = DtnoWithError(title = listOf(), data = listOf())
        coEvery {
            service.getDtno(
                url = capture(urlSlot),
                action = any(),
                dtno = any(),
                paramStr = any(),
                assignSpid = any(),
                keyMap = any(),
                filterNo = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        web.getDtno(4210983, "", "", "", 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getDtno_success() = testScope.runTest {
        val response = DtnoWithError(
            listOf(
                "股票代號",
                "股票名稱",
                "日期",
                "股票名稱1",
                "產業指數代號",
                "上市上櫃"
            ),
            listOf(
                listOf(
                    "0050",
                    "元大台灣50",
                    "20200803",
                    "元大台灣50",
                    "",
                    ""
                )
            )
        )
        coEvery {
            service.getDtno(
                url = any(),
                action = any(),
                dtno = any(),
                paramStr = any(),
                assignSpid = any(),
                keyMap = any(),
                filterNo = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getDtno(4210983, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getDtno_not found`() = testScope.runTest {
        val responseBodyJson = """
            { "Error": {"Code": 101,"Message": "身分驗證錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            service.getDtno(
                url = any(),
                action = any(),
                dtno = any(),
                paramStr = any(),
                assignSpid = any(),
                keyMap = any(),
                filterNo = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result = web.getDtno(0, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getDtno_身分驗證錯誤`() = testScope.runTest {
        val responseBodyJson = """
             { "Error": {"Code": 101,"Message": "身分驗證錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            service.getDtno(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                action = any(),
                dtno = any(),
                paramStr = any(),
                assignSpid = any(),
                keyMap = any(),
                filterNo = any()
            )
        } returns Response.success(responseBody)
        val result = web.getDtno(4210983, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getAfterHoursTime_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        coEvery {
            service.getAfterHoursTime(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(AfterHoursTimeWithError("2021-01-20T00:00:00"))
        web.getAfterHoursTime()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAfterHoursTime_success() = testScope.runTest {
        coEvery {
            service.getAfterHoursTime(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(AfterHoursTimeWithError("2021-01-20T00:00:00"))
        val result = web.getAfterHoursTime()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ParseException::class)
    fun `getAfterHoursTime_format error_ParseException`() = testScope.runTest {
        coEvery {
            service.getAfterHoursTime(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(AfterHoursTimeWithError("2021-01-20T00:00"))
        val result = web.getAfterHoursTime()
        result.getOrThrow()
    }

    @Test
    fun `searchStock_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
        val urlSlot = slot<String>()
        coEvery {
            service.searchStock(
                url = capture(urlSlot),
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(emptyList())
        web.searchStock("0000")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchStock_success() = testScope.runTest {
        val response = listOf(
            ResultEntry("2222", "2222", 1),
            ResultEntry("4444", "4444", 3),
            ResultEntry("6666", "6666", 5)
        )
        coEvery {
            service.searchStock(
                url = any(),
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(response)
        val result = web.searchStock("0000")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(3)
    }

    @Test
    fun `searchStock_not found`() = testScope.runTest {
        val response = emptyList<ResultEntry>()
        coEvery {
            service.searchStock(
                url = any(),
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(response)
        val result = web.searchStock("8888")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(0)
    }

    @Test
    fun `searchUsStock_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
        val urlSlot = slot<String>()
        coEvery {
            service.searchStock(
                url = capture(urlSlot),
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(emptyList())
        web.searchStock("0000")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchUsStock_success() = testScope.runTest {
        val response = listOf(
            UsResultEntry("2222", 1, "2222"),
            UsResultEntry("4444", 3, "4444"),
            UsResultEntry("6666", 5, "6666")
        )
        coEvery {
            service.searchUsStock(
                url = any(),
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(response)
        val result = web.searchUsStock("0000")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(3)
    }

    @Test
    fun `getForeignExchangeTicks_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/ForeignExchangeTrading.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetForeignExchangeTickResponseBody(
            isMarketClosed = null,
            isSuccess = null,
            responseCode = null,
            responseMsg = null,
            tickInfoSet = listOf()
        )
        coEvery {
            service.getForeignExchangeTicks(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                commKeys = any(),
                statusCodes = any()
            )
        } returns Response.success(responseBody)
        web.getForeignExchangeTicks(listOf("SUSDTWD" to 0))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getForeignExchangeTicks_success() = testScope.runTest {
        val responseBody = GetForeignExchangeTickResponseBody(
            isMarketClosed = false,
            isSuccess = true,
            responseCode = 0,
            responseMsg = "",
            tickInfoSet = listOf(
                com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks.TickInfo(
                    buyOrSell = 0,
                    commKey = "SUSDTWD",
                    dealPrice = 27.988,
                    highestPrice = 0.0,
                    investorStatus = 0,
                    limitDown = "0",
                    limitUp = "0",
                    lowestPrice = 0.0,
                    openPrice = 0.0,
                    packageDataType = 0,
                    priceChange = -0.061,
                    quoteChange = -0.2175,
                    refPrice = 0.0,
                    singleVolume = 0,
                    statusCode = 12507,
                    tickTime = 1626233984L,
                    totalVolume = 0
                )
            )
        )
        coEvery {
            service.getForeignExchangeTicks(
                url = any(),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                commKeys = any(),
                statusCodes = any()
            )
        } returns Response.success(responseBody)
        val result = web.getForeignExchangeTicks(listOf("SUSDTWD" to 0))
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(data.isMarketClosed).isFalse()
        Truth.assertThat(data.responseCode).isEqualTo(0)
        Truth.assertThat(data.responseMsg).isEmpty()
        val tickInfoSets = data.tickInfoSet
        Truth.assertThat(tickInfoSets).hasSize(1)
        val tickInfoSet = tickInfoSets!!.first()
        Truth.assertThat(tickInfoSet.buyOrSell).isEqualTo(0)
        Truth.assertThat(tickInfoSet.commKey).isEqualTo("SUSDTWD")
        Truth.assertThat(tickInfoSet.dealPrice).isEqualTo(27.988)
        Truth.assertThat(tickInfoSet.highestPrice).isEqualTo(0.0)
        Truth.assertThat(tickInfoSet.limitDown).isEqualTo("0")
        Truth.assertThat(tickInfoSet.limitUp).isEqualTo("0")
        Truth.assertThat(tickInfoSet.lowestPrice).isEqualTo(0.0)
        Truth.assertThat(tickInfoSet.openPrice).isEqualTo(0.0)
        Truth.assertThat(tickInfoSet.packageDataType).isEqualTo(0)
        Truth.assertThat(tickInfoSet.priceChange).isEqualTo(-0.061)
        Truth.assertThat(tickInfoSet.quoteChange).isEqualTo(-0.2175)
        Truth.assertThat(tickInfoSet.refPrice).isEqualTo(0.0)
        Truth.assertThat(tickInfoSet.singleVolume).isEqualTo(0)
        Truth.assertThat(tickInfoSet.statusCode).isEqualTo(12507)
        Truth.assertThat(tickInfoSet.tickTime).isEqualTo(1626233984L)
        Truth.assertThat(tickInfoSet.totalVolume).isEqualTo(0)
    }

    @Test
    fun `getStockDealDetail_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val response = StockDealDetailWithError(
            timeCode = 0,
            dealInfoSet = listOf(),
            responseCode = 0,
            responseMsg = "",
            isSuccess = true
        )
        coEvery {
            service.getStockDealDetail(
                url = capture(urlSlot),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(response)
        web.getStockDealDetail("2330", 10, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockDealDetail_success() = testScope.runTest {
        val response = StockDealDetailWithError(
            timeCode = 0,
            dealInfoSet = listOf(),
            responseCode = 0,
            responseMsg = "",
            isSuccess = true
        )
        coEvery {
            service.getStockDealDetail(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(response)
        val result = web.getStockDealDetail("2330", 10, 0)
        Truth.assertThat(result.getOrNull()).isEqualTo(response.toRealResponse())
    }

    @Test(expected = TimeoutException::class)
    fun getStockDealDetail_failure_TimeoutException() = testScope.runTest {
        coEvery {
            service.getStockDealDetail(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } answers {
            throw TimeoutException()
        }
        val result = web.getStockDealDetail("2330", 10, 0)
        result.getOrThrow()
    }

    @Test
    fun `getStockDealDetail_failure_commKey error`() = testScope.runTest {
        val expectResponseCode = 100004
        val response = StockDealDetailWithError(
            timeCode = -1,
            dealInfoSet = emptyList(),
            responseCode = expectResponseCode,
            responseMsg = "查不到此檔股票",
            isSuccess = false
        )
        coEvery {
            service.getStockDealDetail(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(response)
        val result = web.getStockDealDetail("1111", 10, 0)
        Truth.assertThat(result.getOrNull()).isEqualTo(response.toRealResponse())
    }

    @Test
    fun getStockDealDetail_failure_AuthFailed() = testScope.runTest {
        val errorJson =
            "{\"Error\":{\"Code\":101,\"Message\":\"Auth Failed\"},\"error\":{\"Code\":101,\"Message\":\"Auth Failed\"}}"
        val responseBody =
            gson.fromJson(errorJson, StockDealDetailWithError::class.java)
        coEvery {
            service.getStockDealDetail(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(responseBody)
        val result = web.getStockDealDetail("1111", 10, 0)
        Truth.assertThat(result.getOrNull()).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = EmptyBodyException::class)
    fun getStockDealDetail_failure_bodyIsNull() = testScope.runTest {
        coEvery {
            service.getStockDealDetail(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(null)
        val result = web.getStockDealDetail("1111", 10, 0)
        result.getOrThrow()
    }

    @Test
    fun `getIsInTradeDay_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val response = GetIsInTradeDayResponseBodyWithError(
            isInTradeDay = true
        )
        coEvery {
            service.getIsInTradeDay(
                url = capture(urlSlot),
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)
        web.getIsInTradeDay()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getIsInTradeDay_success() = testScope.runTest {
        val response = GetIsInTradeDayResponseBodyWithError(
            isInTradeDay = true
        )
        coEvery {
            service.getIsInTradeDay(
                url = any(),
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)
        val result = web.getIsInTradeDay()
        Truth.assertThat(result.getOrNull()).isEqualTo(response.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun getIsInTradeDay_failure_AuthFailed() = testScope.runTest {
        val errorJson =
            "{\"Error\":{\"Code\":101,\"Message\":\"Auth Failed\"},\"error\":{\"Code\":101,\"Message\":\"Auth Failed\"}}"
        val responseBody = gson.fromJson<GetIsInTradeDayResponseBodyWithError>(
            errorJson,
            GetIsInTradeDayResponseBodyWithError::class.java
        )
        coEvery {
            service.getIsInTradeDay(
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result = web.getIsInTradeDay()
        result.getOrThrow()
    }

    @Test
    fun `getStockSinIndex_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/InstantTrading/InstantTrading.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetStocksInIndexResponseBodyWithError(
            stocks = listOf()
        )
        coEvery {
            service.getStockSinIndex(
                url = capture(urlSlot),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
            )
        } returns Response.success(responseBody)
        web.getStockSinIndex(commKey = "TWB12")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockSinIndex_success() = testScope.runTest {
        val responseJSON =
            "{\"Stocks\":[{\"Commkey\":\"1201\",\"CommName\":\"味全\"},{\"Commkey\":\"1203\",\"CommName\":\"味王\"},{\"Commkey\":\"1210\",\"CommName\":\"大成\"},{\"Commkey\":\"1213\",\"CommName\":\"大飲\"},{\"Commkey\":\"1215\",\"CommName\":\"卜蜂\"},{\"Commkey\":\"1216\",\"CommName\":\"統一\"},{\"Commkey\":\"1217\",\"CommName\":\"愛之味\"},{\"Commkey\":\"1218\",\"CommName\":\"泰山\"},{\"Commkey\":\"1219\",\"CommName\":\"福壽\"},{\"Commkey\":\"1220\",\"CommName\":\"台榮\"},{\"Commkey\":\"1225\",\"CommName\":\"福懋油\"},{\"Commkey\":\"1227\",\"CommName\":\"佳格\"},{\"Commkey\":\"1229\",\"CommName\":\"聯華\"},{\"Commkey\":\"1231\",\"CommName\":\"聯華食\"},{\"Commkey\":\"1232\",\"CommName\":\"大統益\"},{\"Commkey\":\"1233\",\"CommName\":\"天仁\"},{\"Commkey\":\"1234\",\"CommName\":\"黑松\"},{\"Commkey\":\"1235\",\"CommName\":\"興泰\"},{\"Commkey\":\"1236\",\"CommName\":\"宏亞\"},{\"Commkey\":\"1256\",\"CommName\":\"鮮活果汁-KY\"},{\"Commkey\":\"1702\",\"CommName\":\"南僑\"},{\"Commkey\":\"1737\",\"CommName\":\"臺鹽\"}]}"
        val response =
            gson.fromJson(responseJSON, GetStocksInIndexResponseBodyWithError::class.java)
        coEvery {
            service.getStockSinIndex(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
            )
        } returns Response.success(response)
        val answer = GetStocksInIndexResponseBody(
            stocks = listOf(
                Stock(commKey = "1201", commName = "味全"),
                Stock(commKey = "1203", commName = "味王"),
                Stock(commKey = "1210", commName = "大成"),
                Stock(commKey = "1213", commName = "大飲"),
                Stock(commKey = "1215", commName = "卜蜂"),
                Stock(commKey = "1216", commName = "統一"),
                Stock(commKey = "1217", commName = "愛之味"),
                Stock(commKey = "1218", commName = "泰山"),
                Stock(commKey = "1219", commName = "福壽"),
                Stock(commKey = "1220", commName = "台榮"),
                Stock(commKey = "1225", commName = "福懋油"),
                Stock(commKey = "1227", commName = "佳格"),
                Stock(commKey = "1229", commName = "聯華"),
                Stock(commKey = "1231", commName = "聯華食"),
                Stock(commKey = "1232", commName = "大統益"),
                Stock(commKey = "1233", commName = "天仁"),
                Stock(commKey = "1234", commName = "黑松"),
                Stock(commKey = "1235", commName = "興泰"),
                Stock(commKey = "1236", commName = "宏亞"),
                Stock(commKey = "1256", commName = "鮮活果汁-KY"),
                Stock(commKey = "1702", commName = "南僑"),
                Stock(commKey = "1737", commName = "臺鹽"),
            )
        )
        val result = web.getStockSinIndex(commKey = "TWB12")
        Truth.assertThat(result.getOrNull()).isEqualTo(answer)
    }

    @Test
    fun getStockSinIndex_failure_AuthFailed() = testScope.runTest {
        val errorJson =
            "{\"Error\":{\"Code\":101,\"Message\":\"Auth Failed\"},\"error\":{\"Code\":101,\"Message\":\"Auth Failed\"}}"
        val responseBody =
            gson.fromJson(errorJson, GetStocksInIndexResponseBodyWithError::class.java)
        coEvery {
            service.getStockSinIndex(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
            )
        } returns Response.success(responseBody)
        val result = web.getStockSinIndex(commKey = "TWB12")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun getStockSinIndex_failure_not_match_commKey() = testScope.runTest {
        val errorJson =
            "{\"Stocks\":[]}"
        val responseBody =
            gson.fromJson(errorJson, GetStocksInIndexResponseBodyWithError::class.java)
        coEvery {
            service.getStockSinIndex(
                url = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
            )
        } returns Response.success(responseBody)
        val answer = GetStocksInIndexResponseBody(stocks = listOf())
        val result = web.getStockSinIndex(commKey = "2330")
        Truth.assertThat(result.getOrNull()).isEqualTo(answer)
    }

    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}