package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.chipk.service.api.futuredaytradedtnodata.FutureDayTradeDtnoWithError
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStock
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfoWithError
import com.cmoney.backend2.chipk.service.api.internationalkchart.ProductType
import com.cmoney.backend2.chipk.service.api.internationalkchart.TickInfo
import com.cmoney.backend2.chipk.service.api.internationalkchart.TickInfoSetWithError
import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ChipKWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val chipKService = mockk<ChipKService>()
    private val gson = Gson()
    private lateinit var chipKWeb: ChipKWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        chipKWeb = ChipKWebImpl(TestSetting(), chipKService, gson, TestDispatcher())
    }

    @Test
    fun `getData_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "K(9)",
                "D(9)"
            ),
            data = listOf(
                listOf(
                    "20121127",
                    "87.59",
                    "75.51"
                )
            )
        )
        coEvery {
            chipKService.getData(
                authorization = any(),
                stockId = any(),
                appId = any(),
                guid = any(),
                type = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getData("2330", 1)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getData_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 2,
                    "Message": "找不到對應的Dtno"
                },
                "Error": {
                    "Code": 2,
                    "Message": "找不到對應的Dtno"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getData(
                authorization = any(),
                stockId = any(),
                appId = any(),
                guid = any(),
                type = any()
            )
        } returns Response.success(responseBody)
        val result = chipKWeb.getData("2330", 5)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getIndexKData_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "開盤價",
                "最高價",
                "最低價",
                "收盤價",
                "成交量",
                "股票名稱",
                "成交金額(千)",
                "漲跌",
                "漲幅(%)"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "633.00",
                    "650.00",
                    "631.00",
                    "647.00",
                    "95484",
                    "台積電",
                    "61279320",
                    "20.00",
                    "3.19"
                )
            )
        )
        coEvery {
            chipKService.getIndexKData(
                authorization = any(),
                commKey = any(),
                appId = any(),
                guid = any(),
                timeRange = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getIndexKData("2330", 1)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getChipKData_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                },
                "Error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getChipKData(
                authorization = any(),
                fundId = any(),
                params = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)
        val result = chipKWeb.getChipKData(0, "_")
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getChipKData_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "開盤價",
                "最高價",
                "最低價",
                "收盤價",
                "成交量",
                "股票名稱",
                "成交金額(千)",
                "漲跌",
                "漲幅(%)"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "633.00",
                    "650.00",
                    "631.00",
                    "647.00",
                    "95484",
                    "台積電",
                    "61279320",
                    "20.00",
                    "3.19"
                )
            )
        )
        coEvery {
            chipKService.getChipKData(
                authorization = any(),
                appId = any(),
                guid = any(),
                fundId = any(),
                params = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getChipKData(0, "_")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getIndexKData_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                },
                "Error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getIndexKData(
                authorization = any(),
                commKey = any(),
                timeRange = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)
        val result = chipKWeb.getIndexKData("2330", 1)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getOfficialStockData_success`() = testScope.runTest {
        val response = OfficialStockInfoWithError(
            replaceSymbol = "周成交量 {#}",
            description = "連 {#} 天賣超",
            stockList = listOf(
                OfficialStock(
                    stockId = "2330",
                    stockName = "台積電",
                    info = "8888"
                )
            )
        )
        coEvery {
            chipKService.getOfficialStockPickData(
                authorization = any(),
                appId = any(),
                guid = any(),
                index = any(),
                type = any()
            )
        } returns Response.success(response)

        val result = chipKWeb.getOfficialStockPickData(0, 2)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getOfficialStockData_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                },
                "Error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, OfficialStockInfoWithError::class.java)
        coEvery {
            chipKService.getOfficialStockPickData(
                authorization = any(),
                appId = any(),
                guid = any(),
                index = any(),
                type = any()
            )
        } returns Response.success(responseBody)
        val result = chipKWeb.getOfficialStockPickData(0, 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getOfficialStockPickTitle_success`() = testScope.runTest {
        val response = JsonArray()
        coEvery {
            chipKService.getOfficialStockPickTitle(
                authorization = any(),
                appId = any(),
                guid = any(),
                type = any()
            )
        } returns Response.success(response)
        Truth.assertThat(response.isJsonArray).isTrue()
        val result = chipKWeb.getOfficialStockPickTitle(2)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getOfficialStockPickTitle_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                },
                "Error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, JsonElement::class.java)
        coEvery {
            chipKService.getOfficialStockPickTitle(
                authorization = any(),
                appId = any(),
                guid = any(),
                type = any()
            )
        } returns Response.success(responseBody)

        val result = chipKWeb.getOfficialStockPickTitle(2)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getIndexForeignInvestment_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "買賣超(億)",
                "淨未平倉",
                "p/cRatio"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "115.2",
                    "149",
                    "-199.9"
                )
            )
        )
        coEvery {
            chipKService.getIndexForeignInvestment(
                authorization = any(),
                appId = any(),
                guid = any(),
                tickCount = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getIndexForeignInvestment(20)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getIndexForeignInvestment_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                },
                "Error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getIndexForeignInvestment(
                authorization = any(),
                appId = any(),
                guid = any(),
                tickCount = any()
            )
        } returns Response.success(responseBody)

        val result = chipKWeb.getIndexForeignInvestment(10)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getIndexMain_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "買賣超(億)",
                "家數差",
                "5日集中(%)",
                "20日集中(%)"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "5.2",
                    "556",
                    "-19.2",
                    "-556"
                )
            )
        )
        coEvery {
            chipKService.getIndexMain(
                authorization = any(),
                appId = any(),
                guid = any(),
                tickCount = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getIndexMain(20)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getIndexMain_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                },
                "Error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getIndexMain(
                authorization = any(),
                appId = any(),
                guid = any(),
                tickCount = any()
            )
        } returns Response.success(responseBody)

        val result = chipKWeb.getIndexMain(10)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getIndexFunded_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "融資變動",
                "融資餘額",
                "融券變動",
                "融券餘額",
                "資使用率",
                "券使用率",
                "當沖張數",
                "借券賣出",
                "借券餘額",
                "當沖比率",
                "融資維持率",
                "券資比"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "5.2",
                    "556",
                    "-19.2",
                    "-556",
                    "-0.25",
                    "1.56",
                    "-66.6",
                    "39.6",
                    "40.5",
                    "-69",
                    "-0.2",
                    "141.5"
                )
            )
        )
        coEvery {
            chipKService.getIndexFunded(
                authorization = any(),
                appId = any(),
                guid = any(),
                tickCount = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getIndexFunded(20)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getIndexFunded_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                },
                "Error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getIndexFunded(
                authorization = any(),
                appId = any(),
                guid = any(),
                tickCount = any()
            )
        } returns Response.success(responseBody)

        val result = chipKWeb.getIndexFunded(20)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getInternationalKChart_success`() = testScope.runTest {
        val tickInfo = TickInfo(
            ceilingPrice = "28146.6800",
            closePrice = "28070.5100",
            date = "20210811",
            lowestPrice = "27974.9900",
            openPrice = "28045.8400",
            priceChange = "182.3600",
            quoteChange = "0.65",
            totalQty = "614308700"
        )
        val response = TickInfoSetWithError(
            tickInfoSet = listOf(tickInfo)
        )
        coEvery {
            chipKService.getInternationalKData(
                authorization = any(),
                action = any(),
                productType = any(),
                productKey = any(),
                appId = any()
            )
        } returns Response.success(response)

        val result = chipKWeb.getInternationalKChart("#N225", ProductType.InternationalIndex)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.tickInfoSet).hasSize(1)
        Truth.assertThat(data.tickInfoSet?.first()).isEqualTo(tickInfo)
    }

    @Test
    fun `getInternationalKChart_failure`() = testScope.runTest {
        val errorResponseBodyJson = """
            {
                "error": {
                    "Code": 2,
                    "Message": "找不到對應的Dtno"
                },
                "Error": {
                    "Code": 2,
                    "Message": "找不到對應的Dtno"
                }
            }
        """.trimIndent()
        val errorResponseBody = gson.fromJson(errorResponseBodyJson, TickInfoSetWithError::class.java)
        coEvery {
            chipKService.getInternationalKData(
                authorization = any(),
                action = any(),
                productType = any(),
                productKey = any(),
                appId = any()
            )
        } returns Response.success(errorResponseBody)

        val result = chipKWeb.getInternationalKChart("#N225", ProductType.InternationalIndex)
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(2)
    }

    @Test
    fun `getCreditRate_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "融資維持率(%):期底"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "186.6688"
                )
            )
        )
        coEvery {
            chipKService.getCreditRate(
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getCreditRate()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getCreditRate_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                },
                "Error": {
                    "Code": 9001,
                    "Message": "參數轉換錯誤"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getCreditRate(
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = chipKWeb.getCreditRate()
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getIndexCalculateRate_success`() = testScope.runTest {
        val response = DtnoWithError(
            title = listOf(
                "日期",
                "K(9)",
                "D(9)",
                "DIF",
                "MACD",
                "DIF-MACD",
                "RSI(5)",
                "RSI(10)"
            ),
            data = listOf(
                listOf(
                    "20210120",
                    "79.9",
                    "6.6",
                    "54.532",
                    "47.796",
                    "45.012",
                    "23.642",
                    "67.68"
                )
            )
        )
        coEvery {
            chipKService.getIndexCalculateRate(
                authorization = any(),
                appId = any(),
                guid = any(),
                commKey = any(),
                timeRange = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getIndexCalculateRate("TWA00", 1)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getIndexCalculateRate_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                },
                "Error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, DtnoWithError::class.java)
        coEvery {
            chipKService.getIndexCalculateRate(
                authorization = any(),
                commKey = any(),
                timeRange = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)
        val result = chipKWeb.getIndexCalculateRate("TWA00", 1)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getFutureDayTradeIndexAnalysis_success`() = testScope.runTest {
        val response = FutureDayTradeDtnoWithError(
            title = listOf(
                "外資淨未平倉",
                "外資期貨未平倉增減",
                "散戶淨未平倉",
                "散戶期貨未平倉增減",
                "三大法人買賣超(億)",
                "外資買賣超(億)",
                "投信買賣超(億)",
                "自營商買賣超(億)",
                "官股買賣超(億)",
                "融資變動(億)",
                "融券變動(億)",
                "盤勢多空"
            ),
            data = listOf(
                "4661",
                "-1522.00",
                "14778",
                "5568.00",
                "-168.5",
                "-140.5",
                "1.0",
                "-28.9",
                "-2.71",
                "-10.29",
                "-1.12",
                "強空"
            )
        )
        coEvery {
            chipKService.getFutureDayTradeIndexAnalysis(
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = chipKWeb.getFutureDayTradeIndexAnalysis()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isNotNull()
    }

    @Test
    fun `getFutureDayTradeIndexAnalysis_failure`() = testScope.runTest {
        val responseBodyJson = """
            {
                "error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                },
                "Error": {
                    "Code": 3,
                    "Message": "找不到對應的Dtno"
                }
            }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, FutureDayTradeDtnoWithError::class.java)
        coEvery {
            chipKService.getFutureDayTradeIndexAnalysis(
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)
        val result = chipKWeb.getFutureDayTradeIndexAnalysis()
        Truth.assertThat(result.isSuccess).isFalse()
    }
}

