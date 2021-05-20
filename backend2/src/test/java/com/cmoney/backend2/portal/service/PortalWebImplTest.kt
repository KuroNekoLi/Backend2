package com.cmoney.backend2.portal.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.portal.MainCoroutineRule
import com.cmoney.backend2.portal.TestDispatcher
import com.cmoney.backend2.portal.TestSetting
import com.cmoney.backend2.portal.service.api.getadditionalinfo.CmPortalAdditionWithError
import com.cmoney.backend2.portal.service.api.getsignals.CmPortalSignalWithError
import com.cmoney.backend2.portal.service.api.gettarget.CmPortalTargetWithError
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.MockKMatcherScope
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PortalWebImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var portalService: PortalService

    private lateinit var portalWeb: PortalWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        portalWeb = PortalWebImpl(
            gson = gson,
            service = portalService,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    private fun <T> checkServerException(result: Result<T>, errorCode: Int) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(errorCode)
    }

    @Test
    fun `getTarget_response code is 1_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CmPortalTargetWithError(
            tseAndOtc = listOf("1101", "1102"),
            stockFutures = listOf("1101", "1102", "1103"),
            dayTrade = listOf("1101", "1102", "1103", "1104"),
            issuanceOfConvertibleBonds = listOf("1101", "1102", "1103", "1104", "1105"),
            issuanceOfWarrants = listOf("1101", "1102", "1103", "1104", "1105", "1106")
        )
        coEvery {
            requestGetTarget()
        } returns Response.success(responseBody)

        val result = portalWeb.getTarget()
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.tseAndOtc).isNotEmpty()
        Truth.assertThat(data.tseAndOtc!!.size).isEqualTo(2)
        Truth.assertThat(data.stockFutures).isNotEmpty()
        Truth.assertThat(data.stockFutures!!.size).isEqualTo(3)
        Truth.assertThat(data.dayTrade).isNotEmpty()
        Truth.assertThat(data.dayTrade!!.size).isEqualTo(4)
        Truth.assertThat(data.issuanceOfConvertibleBonds).isNotEmpty()
        Truth.assertThat(data.issuanceOfConvertibleBonds!!.size).isEqualTo(5)
        Truth.assertThat(data.issuanceOfWarrants).isNotEmpty()
        Truth.assertThat(data.issuanceOfWarrants!!.size).isEqualTo(6)
    }

    @Test
    fun `getTarget_response code is 100_參數錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalTargetWithError>(
            responseBodyJson,
            CmPortalTargetWithError::class.java
        )
        coEvery {
            requestGetTarget()
        } returns Response.success(responseBody)

        val result = portalWeb.getTarget()
        checkServerException(result, 100)
    }

    @Test
    fun `getTarget_response code is 101_身分驗證錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalTargetWithError>(
            responseBodyJson,
            CmPortalTargetWithError::class.java
        )
        coEvery {
            requestGetTarget()
        } returns Response.success(responseBody)

        val result = portalWeb.getTarget()
        checkServerException(result, 101)
    }

    @Test
    fun `getSignals_response code is 1_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CmPortalSignalWithError(
            columns = listOf(
                "CommKey",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15"
            ),
            rows = listOf(
                listOf(
                    "3056",
                    "1",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "1",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0"
                ),
                listOf(
                    "3552",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "1",
                    "0",
                    "0",
                    "1",
                    "0",
                    "1",
                    "0"
                )
            )
        )
        coEvery {
            requestGetSignals()
        } returns Response.success(responseBody)

        val result = portalWeb.getSignals()
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.columns).isNotEmpty()
        Truth.assertThat(data.columns!!.size).isEqualTo(16)
        Truth.assertThat(data.rows).isNotEmpty()
        Truth.assertThat(data.rows!!.size).isEqualTo(2)
    }

    @Test
    fun `getSignals_response code is 100_參數錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()

        val responseBody = gson.fromJson<CmPortalSignalWithError>(
            responseBodyJson,
            CmPortalSignalWithError::class.java
        )

        coEvery {
            requestGetSignals()
        } returns Response.success(responseBody)

        val result = portalWeb.getSignals()
        checkServerException(result, 100)
    }

    @Test
    fun `getSignals_response code is 101_身分驗證錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalSignalWithError>(
            responseBodyJson,
            CmPortalSignalWithError::class.java
        )
        coEvery {
            requestGetSignals()
        } returns Response.success(responseBody)

        val result = portalWeb.getSignals()
        checkServerException(result, 101)
    }

    @Test
    fun `getAdditionalInfo_response code is 1_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CmPortalAdditionWithError(
            columns = listOf("標的", "即時成交價", "漲跌幅", "總量", "五日均量"),
            rows = listOf(listOf("1301", "103", "-9.25", "26307", "8502.4"))
        )
        coEvery {
            requestGetAdditionalStockInfo()
        } returns Response.success(responseBody)

        val result = portalWeb.getAdditionalInfo(STOCK_INFO)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.columns).isNotEmpty()
        Truth.assertThat(data.columns!!.size).isEqualTo(5)
        Truth.assertThat(data.rows).isNotEmpty()
        Truth.assertThat(data.rows!!.size).isEqualTo(1)
    }

    @Test
    fun `getAdditionalInfo_response code is 100_參數錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()

        val responseBody = gson.fromJson<CmPortalAdditionWithError>(
            responseBodyJson,
            CmPortalAdditionWithError::class.java
        )

        coEvery {
            requestGetAdditionalStockInfo()
        } returns Response.success(responseBody)

        val result = portalWeb.getAdditionalInfo(STOCK_INFO)
        checkServerException(result, 100)
    }

    @Test
    fun `getAdditionalInfo_response code is 101_身分驗證錯誤`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalAdditionWithError>(
            responseBodyJson,
            CmPortalAdditionWithError::class.java
        )
        coEvery {
            requestGetAdditionalBasicInfo()
        } returns Response.success(responseBody)

        val result = portalWeb.getAdditionalInfo(BASIC_INFO)
        checkServerException(result, 101)
    }

    private suspend fun MockKMatcherScope.requestGetTarget(): Response<CmPortalTargetWithError> {
        return portalService.getTarget(
            authorization = any(),
            body = any()
        )
    }

    private suspend fun MockKMatcherScope.requestGetSignals(): Response<CmPortalSignalWithError> {
        return portalService.getSignals(
            authorization = any(),
            body = any()
        )
    }

    private suspend fun MockKMatcherScope.requestGetAdditionalStockInfo(): Response<CmPortalAdditionWithError> {
        return portalService.getAdditionalInfo(
            authorization = any(),
            body = any()
        )
    }

    private suspend fun MockKMatcherScope.requestGetAdditionalBasicInfo(): Response<CmPortalAdditionWithError> {
        return portalService.getAdditionalInfo(
            authorization = any(),
            body = any()
        )
    }

    companion object {
        // getAdditionalInfo settingId type
        private const val STOCK_INFO = 1 // 1: 價量區 股票資訊
        private const val BASIC_INFO = 2 // 2: 基本資料區
    }

}