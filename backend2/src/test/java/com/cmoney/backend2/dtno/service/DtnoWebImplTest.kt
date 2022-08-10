package com.cmoney.backend2.dtno.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoData
import com.cmoney.backend2.dtno.service.api.getLatestBasicInfo.BasicInfoResponseBodyWithError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class DtnoWebImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var service: DtnoService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var dtnoWeb: DtnoWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        dtnoWeb = DtnoWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcherProvider = TestDispatcher()
        )
    }

    @Test
    fun `getKLineData_成功`() = mainCoroutineRule.runTest {
        val response = DtnoWithError(
            listOf("日期", "開盤價", "最高價", "最低價", "收盤價", "成交量", "漲跌", "漲幅(%)", "成交金額(千)"),
            listOf(
                listOf(
                    "20200101",
                    "12345.6",
                    "12234.5",
                    "12456.7",
                    "12345.6",
                    "987654",
                    "50.0",
                    "30.0",
                    "1000"
                ),
                listOf(
                    "20200102",
                    "12378.9",
                    "12789.9",
                    "12345.6",
                    "12365.5",
                    "8765432",
                    "40.0",
                    "25.0",
                    "1500"
                )
            )
        )
        coEvery {
            service.getKLineData(
                authorization = any(),
                commKey = any(),
                timeRangeType = any(),
                number = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = dtnoWeb.getKLineData("TWA00", 1, 1)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(2)
    }

    @Test
    fun `getKLineData_參數錯誤`() = mainCoroutineRule.runTest {
        val response = DtnoWithError(
            listOf("日期", "開盤價", "最高價", "最低價", "收盤價", "成交量", "漲跌", "漲幅(%)", "成交金額(千)"),
            listOf()
        )
        coEvery {
            service.getKLineData(
                commKey = any(),
                timeRangeType = any(),
                number = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)

        val result = dtnoWeb.getKLineData("0000", 0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(0)
    }

    @Test
    fun `getLatestBasicInfo_成功`() = mainCoroutineRule.runTest {
        val response = BasicInfoResponseBodyWithError(
            listOf(
                BasicInfoData("TSM", "73.90", "9.69", "6.53", "20200724"),
                BasicInfoData("CHT", "65.70", "5.12", "1.21", "20200724")
            )
        )
        coEvery {
            service.getLatestBasicInfo(
                commKeys = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                appServiceId = any()
            )
        } returns Response.success(response)
        val result = dtnoWeb.getLatestBasicInfo(
            commKeys = listOf("TSM", "CHT"),
            appServiceId = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.basicInfoList?.size).isEqualTo(2)
    }

    @Test
    fun `getLatestBasicInfo_參數錯誤`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error": {"Code": 100,"Message": "參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, BasicInfoResponseBodyWithError::class.java)
        coEvery {
            service.getLatestBasicInfo(
                commKeys = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                appServiceId = any()
            )
        } returns Response.success(responseBody)
        val result = dtnoWeb.getLatestBasicInfo(
            commKeys = listOf(),
            appServiceId = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getLatestBasicInfo_身分驗證錯誤`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            { "Error": {"Code": 101,"Message": "身分驗證錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, BasicInfoResponseBodyWithError::class.java)
        coEvery {
            service.getLatestBasicInfo(
                commKeys = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                appServiceId = any()
            )
        } returns Response.success(responseBody)
        val result = dtnoWeb.getLatestBasicInfo(
            commKeys = listOf("TSM", "CHT"),
            appServiceId = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }
}
