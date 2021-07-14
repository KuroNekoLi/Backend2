package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.GuestApiParam
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.base.model.response.error.ISuccess
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.realtimeaftermarket.MainCoroutineRule
import com.cmoney.backend2.realtimeaftermarket.TestDispatcher
import com.cmoney.backend2.realtimeaftermarket.TestSetting
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks.InternationalNewTicks
import com.cmoney.backend2.realtimeaftermarket.service.api.getafterhourstime.AfterHoursTimeWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.GetCommListResponseBody
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.Product
import com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist.ProductInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail.StockDealDetailWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday.GetIsInTradeDayResponseBodyWithError
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick.MarketNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.NewTickInfo
import com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo.TickInfoSet
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.ChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.GroupedPriceVolumeData
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.InvestorChartData
import com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick.SingleStockNewTick
import com.cmoney.backend2.realtimeaftermarket.service.api.searchstock.ResultEntry
import com.cmoney.backend2.realtimeaftermarket.service.api.searchustock.UsResultEntry
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: RealTimeAfterMarketService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var webImpl: RealTimeAfterMarketWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        webImpl = RealTimeAfterMarketWebImpl(
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `getCommList_成功`() = mainCoroutineRule.runBlockingTest {
        val mockResponse = GetCommListResponseBody(
            products = listOf(
                Product(
                    1,
                    listOf(
                        ProductInfo(
                            commkey = "MYMF1",
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
                authorization = any(),
                areaIds = any(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberId()
            )
        } returns Response.success(mockResponse)
        val result = webImpl.getCommList(listOf("1"))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.responseCode).isEqualTo(1)
        Truth.assertThat(data.products?.size).isEqualTo(1)
        val product = data.products?.firstOrNull()!!
        Truth.assertThat(product.areaId).isEqualTo(1)
        Truth.assertThat(product.productInfos?.size).isEqualTo(1)
        val productInfo = product.productInfos!!.first()
        Truth.assertThat(productInfo.commkey).isEqualTo("MYMF1")
        Truth.assertThat(productInfo.name).isEqualTo("小道瓊期貨")
        Truth.assertThat(productInfo.countryCode).isEqualTo(840)
        Truth.assertThat(productInfo.isShowPreviousClosePr).isFalse()
    }

    @Test
    fun `searchStock_成功`() = mainCoroutineRule.runBlockingTest {
        val response = listOf(
            ResultEntry("2222", "2222", 1),
            ResultEntry("4444", "4444", 3),
            ResultEntry("6666", "6666", 5)
        )
        coEvery {
            service.searchStock(
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(response)
        val result = webImpl.searchStock("0000")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(3)
    }

    @Test
    fun `searchStock_無搜尋結果`() = mainCoroutineRule.runBlockingTest {
        val response = emptyList<ResultEntry>()
        coEvery {
            service.searchStock(
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(response)
        val result = webImpl.searchStock("8888")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(0)
    }

    @Test
    fun `searchUsStock_成功`() = mainCoroutineRule.runBlockingTest {
        val response = listOf(
            UsResultEntry("2222", 1, "2222"),
            UsResultEntry("4444", 3, "4444"),
            UsResultEntry("6666", 5, "6666")
        )
        coEvery {
            service.searchUsStock(
                authorization = any(),
                queryKey = any()
            )
        } returns Response.success(response)
        val result = webImpl.searchUsStock("0000")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(3)
    }

    @Test
    fun `getNewTickInfo_成功`() = mainCoroutineRule.runBlockingTest {
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
                    singleVolume = 3564,
                    totalVolume = 123789,
                    tickTime = 654987L,
                    statusCode = 1,
                    packageDataType = 2,
                    investorStatus = 3
                )
            )
        )
        coEvery {
            service.getNewTickInfo(
                commKeys = any(),
                statusCodes = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getNewTickInfo(listOf("1111"), listOf(1))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getNewTickInfo_失敗`() = mainCoroutineRule.runBlockingTest {
        val response = NewTickInfo(
            isMarketClosed = true,
            isSuccess = false,
            responseCode = ISuccess.DEFAULT_ERROR_CODE,
            responseMsg = ISuccess.DEFAULT_ERROR_MESSAGE,
            tickInfoSet = emptyList()
        )
        coEvery {
            service.getNewTickInfo(
                commKeys = any(),
                statusCodes = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getNewTickInfo(emptyList(), emptyList())
        checkServerException(result)
    }

    @Test
    fun `getSingleStockNewTick_成功`() = mainCoroutineRule.runBlockingTest {
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
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getSingleStockLongNewTick("1111", "0")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getSingleStockNewTick_失敗`() = mainCoroutineRule.runBlockingTest {
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
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getSingleStockLongNewTick("", "")
        checkServerException(result)
    }

    @Test
    fun `getMarketNewTick_成功`() = mainCoroutineRule.runBlockingTest {
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
                commkey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getMarketNewTick("1111", "0")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getMarketNewTick_失敗`() = mainCoroutineRule.runBlockingTest {
        val response = MarketNewTick(
            isMarketClosed = false,
            tickInfoSet = emptyList(),
            isSuccess = false,
            responseCode = ISuccess.DEFAULT_ERROR_CODE,
            responseMsg = ISuccess.DEFAULT_ERROR_MESSAGE
        )
        coEvery {
            service.getMarketNewTick(
                commkey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getMarketNewTick("", "")
        checkServerException(result)
    }

    @Test
    fun `getInternationalNewTick_成功`() = mainCoroutineRule.runBlockingTest {
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
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getInternationalNewTick("1111", "0")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getInternationalNewTick_失敗`() = mainCoroutineRule.runBlockingTest {
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
                commKey = any(),
                statusCode = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = webImpl.getInternationalNewTick("", "")
        checkServerException(result)
    }

    @Test
    fun `getDtno_成功_訪客`() = mainCoroutineRule.runBlockingTest {
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
                ),
                listOf(
                    "0051",
                    "元大中型100",
                    "20200803",
                    "元大中型100",
                    "",
                    ""
                )
            )
        )
        coEvery {
            service.getDtno(
                authorization = any(),
                guid = any(),
                action = any(),
                dtno = any(),
                paramStr = any(),
                assignSpid = any(),
                keyMap = any(),
                filterNo = any(),
                appId = any()
            )
        } returns Response.success(response)
        val guestApiParam = GuestApiParam(
            appId = 99
        )
        val result = webImpl.getDtno(guestApiParam, 4210983, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getDtno_成功_身分識別`() = mainCoroutineRule.runBlockingTest {
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
        val result = webImpl.getDtno(4210983, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getDtno_空表`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            { "Error": {"Code": 101,"Message": "身分驗證錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            service.getDtno(
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
        val result = webImpl.getDtno(0, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getDtno_身分驗證錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
             { "Error": {"Code": 101,"Message": "身分驗證錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            service.getDtno(
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
        val result = webImpl.getDtno(4210983, "", "", "", 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getAfterHoursTime_成功`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getAfterHoursTime(
                    authorization = any(),
                    appId = any(),
                    guid = any()
            )
        } returns Response.success(AfterHoursTimeWithError("2021-01-20T00:00:00"))
        val result = webImpl.getAfterHoursTime()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ParseException::class)
    fun `getAfterHoursTime_日期格式錯誤`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getAfterHoursTime(
                    authorization = any(),
                    appId = any(),
                    guid = any()
            )
        } returns Response.success(AfterHoursTimeWithError("2021-01-20T00:00"))
        val result = webImpl.getAfterHoursTime()
        result.getOrThrow()
    }

    @Test(expected = TimeoutException::class)
    fun `getStockDealDetail_TimeoutException`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getStockDealDetail(
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
        val result = webImpl.getStockDealDetail("2330", 10, 0)
        result.getOrThrow()
    }

    @Test
    fun `getStockDealDetail_成功`() = mainCoroutineRule.runBlockingTest {
        val response = StockDealDetailWithError(
            timeCode = 0,
            dealInfoSet = listOf(),
            responseCode = 0,
            responseMsg = "",
            isSuccess = true
        )
        coEvery {
            service.getStockDealDetail(
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(response)
        val result = webImpl.getStockDealDetail("2330", 10, 0)
        Truth.assertThat(result.getOrNull()).isEqualTo(response.toRealResponse())
    }

    @Test
    fun `getStockDealDetail_失敗_股票代號錯誤`() = mainCoroutineRule.runBlockingTest {
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
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(response)
        val result = webImpl.getStockDealDetail("1111", 10, 0)
        Truth.assertThat(result.getOrNull()).isEqualTo(response.toRealResponse())
    }

    @Test
    fun `getStockDealDetail_失敗_AuthFailed`() = mainCoroutineRule.runBlockingTest {
        val errorJson = "{\"Error\":{\"Code\":101,\"Message\":\"Auth Failed\"},\"error\":{\"Code\":101,\"Message\":\"Auth Failed\"}}"
        val responseBody = gson.fromJson<StockDealDetailWithError>(errorJson, StockDealDetailWithError::class.java)
        coEvery {
            service.getStockDealDetail(
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(responseBody)
        val result = webImpl.getStockDealDetail("1111", 10, 0)
        Truth.assertThat(result.getOrNull()).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = EmptyBodyException::class)
    fun `getStockDealDetail_失敗_bodyIsNull`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getStockDealDetail(
                commKey = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                timeCode = any(),
                perReturnCode = any()
            )
        } returns Response.success(null)
        val result = webImpl.getStockDealDetail("1111", 10, 0)
        result.getOrThrow()
    }

    @Test
    fun `getIsInTradeDay_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetIsInTradeDayResponseBodyWithError(
            isInTradeDay = true
        )
        coEvery {
            service.getIsInTradeDay(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)
        val result = webImpl.getIsInTradeDay()
        Truth.assertThat(result.getOrNull()).isEqualTo(response.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `getIsInTradeDay_失敗_AuthFailed`() = mainCoroutineRule.runBlockingTest {
        val errorJson = "{\"Error\":{\"Code\":101,\"Message\":\"Auth Failed\"},\"error\":{\"Code\":101,\"Message\":\"Auth Failed\"}}"
        val responseBody = gson.fromJson<GetIsInTradeDayResponseBodyWithError>(errorJson, GetIsInTradeDayResponseBodyWithError::class.java)
        coEvery {
            service.getIsInTradeDay(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result = webImpl.getIsInTradeDay()
        result.getOrThrow()
    }

    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }
}