package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.testing.CandleChartRequest
import com.cmoney.backend2.additioninformationrevisit.service.testing.SomeTickRequest
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AdditionalInformationRevisitUsWebImplTest {

    private val testScope = TestScope()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule(testScope = testScope)
    private lateinit var web: AdditionalInformationRevisitWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @MockK
    private lateinit var service: AdditionalInformationRevisitService
    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .serializeSpecialFloatingPointValues()
        .create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = AdditionalInformationRevisitWebImpl(
            globalBackend2Manager = manager,
            service = service,
            marketType = AdditionalInformationRevisitWeb.MarketType.US,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getAdditionInformationRevisitUsSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
        coEvery {
            manager.getAdditionInformationRevisitUsSettingAdapter().getPathName()
        } returns EXCEPT_PATH_NAME
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getAll_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GetAll/StockCalculation"
        val urlSlot = slot<String>()
        coEvery {
            service.getAll(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        web.getAll(listOf(), "StockCalculation", emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getAll(
            domain = EXCEPT_DOMAIN,
            columns = listOf(),
            typeName = "StockCalculation",
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getAll(
            columns = listOf(),
            typeName = "StockCalculation",
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getAll_success_ListListString() = testScope.runTest {
        coEvery {
            service.getAll(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        val response = web.getAll(listOf(), typeName, emptyList())
        Truth.assertThat(response.isSuccess).isTrue()
    }


    @Test
    fun getAll_failed_401() = testScope.runTest {
        coEvery {
            service.getAll(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getAll(
            columns = emptyList(),
            typeName = "StockCalculation",
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getTarget_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GetTarget/StockCalculation"
        val urlSlot = slot<String>()
        coEvery {
            service.getTarget(
                url = capture(urlSlot),
                authorization = any(),
                keyNamePath = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        web.getTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getTarget(
            domain = EXCEPT_DOMAIN,
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getTarget_success_ListListString() = testScope.runTest {
        coEvery {
            service.getTarget(
                url = any(),
                authorization = any(),
                keyNamePath = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        val response = web.getTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getTarget_failed_401() = testScope.runTest {
        coEvery {
            service.getTarget(
                url = any(),
                authorization = any(),
                keyNamePath = any(),
                columns = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val typeName = "StockCalculation"
        val result = web.getTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getSignal_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Signal/Get/4218074,4217863,4218054"
        val urlSlot = slot<String>()
        coEvery {
            service.getSignal(
                url = capture(urlSlot),
                authorization = any(),
                param = any()
            )
        } returns Response.success(emptyList())
        val channels = listOf("4218074", "4217863", "4218054")
        web.getSignal(channels = channels)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getSignal(domain = EXCEPT_DOMAIN, channels = channels)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getSignal(channels = channels, url = "custom url")
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getSignal_success_ListListString() = testScope.runTest {
        coEvery {
            service.getSignal(
                url = any(),
                authorization = any(),
                param = any()
            )
        } returns Response.success(
            listOf(
                listOf("2330", "1620696741000", "true", "true", "true")
            )
        )
        val channels = listOf("4218074", "4217863", "4218054")
        val response = web.getSignal(channels)
        Truth.assertThat(response.isSuccess).isTrue()
        response.getOrThrow().forEach { oneRowData ->
            Truth.assertThat(oneRowData.size).isEqualTo(channels.size + 2)
            oneRowData.forEachIndexed { index, isMatch ->
                if (index > 1) {
                    Truth.assertThat(isMatch.toBoolean()).isTrue()
                }
            }
        }
    }

    @Test
    fun getSignal_failed_401() = testScope.runTest {
        coEvery {
            service.getSignal(
                url = any(),
                authorization = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val channels = listOf<String>("4218074", "4217863", "4218054")
        val result = web.getSignal(channels)
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getMultiple_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GetMultiple/CandleStockChart"
        val urlSlot = slot<String>()
        coEvery {
            service.getMultiple(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "CandleStockChart"
        web.getMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getMultiple(
            domain = EXCEPT_DOMAIN,
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getMultiple_success_ListListString() = testScope.runTest {
        coEvery {
            service.getMultiple(
                url = any(),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "CandleStockChart"
        val response = web.getMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getMultiple_failed_401() = testScope.runTest {
        coEvery {
            service.getMultiple(
                url = any(),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val typeName = "CandleStockChart"
        val result = web.getMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getOtherQuery_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GetOtherQuery/SectionTransactionDetailsRequest/IEnumerable<ITick<ICommodity>>"
        val urlSlot = slot<String>()
        coEvery {
            service.getOtherQuery(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val columns = listOf("標的")
        val responseType = "IEnumerable<ITick<ICommodity>>"
        web.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getOtherQuery(
            domain = EXCEPT_DOMAIN,
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getOtherQuery_success_ListListString() = testScope.runTest {
        coEvery {
            service.getOtherQuery(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val columns = listOf("標的")
        val responseType = "IEnumerable<ITick<ICommodity>>"
        val response = web.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getOtherQuery_failed_401() = testScope.runTest {
        coEvery {
            service.getOtherQuery(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val columns = listOf("標的")
        val responseType = "IEnumerable<ITick<ICommodity>>"
        val result = web.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getPreviousAll_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/PreviousData/GetAll/StockCalculation"
        val urlSlot = slot<String>()
        coEvery {
            service.getPreviousAll(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        web.getPreviousAll(
            columns = emptyList(),
            typeName = typeName,
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousAll(
            domain = EXCEPT_DOMAIN,
            columns = emptyList(),
            typeName = typeName,
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousAll(
            columns = emptyList(),
            typeName = typeName,
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getPreviousAll_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousAll(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        val response = web.getPreviousAll(
            columns = emptyList(),
            typeName = typeName,
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }


    @Test
    fun getPreviousAll_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousAll(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getPreviousAll(
            columns = emptyList(),
            typeName = "StockCalculation",
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getPreviousTarget_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/PreviousData/GetTarget/StockCalculation"
        val urlSlot = slot<String>()
        coEvery {
            service.getPreviousTarget(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        web.getPreviousTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousTarget(
            domain = EXCEPT_DOMAIN,
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getPreviousTarget_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousTarget(
                url = any(),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        val response = web.getPreviousTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getPreviousTarget_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousTarget(
                url = any(),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val typeName = "StockCalculation"
        val result = web.getPreviousTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getPreviousMultiple_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/PreviousData/GetMultiple/CandleStockChart"
        val urlSlot = slot<String>()
        coEvery {
            service.getPreviousMultiple(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "CandleStockChart"
        web.getPreviousMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousMultiple(
            domain = EXCEPT_DOMAIN,
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getPreviousMultiple_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousMultiple(
                url = any(),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val typeName = "CandleStockChart"
        val response = web.getPreviousMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getPreviousMultiple_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousMultiple(
                url = any(),
                authorization = any(),
                columns = any(),
                keyNamePath = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val typeName = "CandleStockChart"
        val result = web.getPreviousMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getPreviousOtherQuery_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/PreviousData/GetOtherQuery/SectionTransactionDetailsRequest/IEnumerable<ITick<ICommodity>>"
        val urlSlot = slot<String>()
        coEvery {
            service.getPreviousOtherQuery(
                url = capture(urlSlot),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val columns = listOf("標的")
        val responseType = "IEnumerable<ITick<ICommodity>>"
        web.getPreviousOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousOtherQuery(
            domain = EXCEPT_DOMAIN,
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        web.getPreviousOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList(),
            url = "custom url"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo("custom url")
    }

    @Test
    fun getPreviousOtherQuery_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousOtherQuery(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.success(
            emptyList()
        )
        val columns = listOf("標的")
        val responseType = "IEnumerable<ITick<ICommodity>>"
        val response = web.getPreviousOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getPreviousOtherQuery_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousOtherQuery(
                url = any(),
                authorization = any(),
                columns = any(),
                param = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val columns = listOf("標的")
        val responseType = "IEnumerable<ITick<ICommodity>>"
        val result = web.getPreviousOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
        private const val EXCEPT_PATH_NAME = "AdditionInformationRevisit/"
    }
}