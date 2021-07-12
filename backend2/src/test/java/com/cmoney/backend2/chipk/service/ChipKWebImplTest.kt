package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStock
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfoWithError
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ChipKWebImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

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
    fun `getData_success`() = mainCoroutineRule.runBlockingTest {
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
    fun `getData_failure`() = mainCoroutineRule.runBlockingTest {
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
    fun `getIndexKData_success`() = mainCoroutineRule.runBlockingTest {
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
    fun `getChipKData_failure`() = mainCoroutineRule.runBlockingTest {
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
    fun `getChipKData_success`() = mainCoroutineRule.runBlockingTest {
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
    fun `getIndexKData_failure`() = mainCoroutineRule.runBlockingTest {
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
    fun `getOfficialStockData_success`() = mainCoroutineRule.runBlockingTest {
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
    fun `getOfficialStockData_failure`() = mainCoroutineRule.runBlockingTest {
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
    fun `getOfficialStockPickTitle_success`() = mainCoroutineRule.runBlockingTest {
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
    fun `getOfficialStockPickTitle_failure`() = mainCoroutineRule.runBlockingTest {
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
}

