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
import io.mockk.mockk
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
class AdditionalInformationRevisitWebImplTest {

    private val testScope = TestScope()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule(testScope = testScope)
    private lateinit var web: AdditionalInformationRevisitWeb
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager
    @MockK
    private lateinit var service: AdditionalInformationRevisitService
    private val gson =  GsonBuilder()
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
            dispatcher = TestDispatcherProvider()
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAll_success_ListListString() = testScope.runTest {
        coEvery { service.getAll(any(), any(), any(), any()) } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        val response = web.getAll(listOf(), typeName, emptyList())
        Truth.assertThat(response.isSuccess).isTrue()
    }


    @Test
    fun getAll_failed_401() = testScope.runTest {
        coEvery {
            service.getAll(any(), any(), any(), any())
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
    fun getTarget_success_ListListString() = testScope.runTest {
        coEvery {
            service.getTarget(
                any(),
                any(),
                any(),
                any(),
                any()
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
                any(),
                any(),
                any(),
                any(),
                any()
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
    fun getMultiple_success_ListListString() = testScope.runTest {
        coEvery {
            service.getMultiple(
                any(),
                any(),
                any(),
                any(),
                any()
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
                any(),
                any(),
                any(),
                any(),
                any()
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
    fun getOtherQuery_success_ListListString() = testScope.runTest {
        coEvery {
            service.getOtherQuery(
                any(),
                any(),
                any(),
                any()
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
                any(),
                any(),
                any(),
                any()
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
    fun getSignal_success_ListListString() = testScope.runTest {
        coEvery { service.getSignal(any(), any(), any()) } returns Response.success(
            listOf(
                listOf("2330", "1620696741000", "true", "true", "true")
            )
        )
        val channels = listOf<String>("4218074", "4217863", "4218054")
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
            service.getSignal(any(), any(), any())
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
    fun getYesterdayAll_success_ListListString() = testScope.runTest {
        coEvery { service.getPreviousAll(any(), any(), any(), any()) } returns Response.success(
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
    fun getYesterdayAll_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousAll(any(), any(), any(), any())
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
    fun getYesterdayTarget_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousTarget(
                any(),
                any(),
                any(),
                any(),
                any()
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
    fun getYesterdayTarget_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousTarget(
                any(),
                any(),
                any(),
                any(),
                any()
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
    fun getYesterdayMultiple_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousMultiple(
                any(),
                any(),
                any(),
                any(),
                any()
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
    fun getYesterdayMultiple_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousMultiple(
                any(),
                any(),
                any(),
                any(),
                any()
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
    fun getYesterdayOtherQuery_success_ListListString() = testScope.runTest {
        coEvery {
            service.getPreviousOtherQuery(
                any(),
                any(),
                any(),
                any()
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
    fun getYesterdayOtherQuery_failed_401() = testScope.runTest {
        coEvery {
            service.getPreviousOtherQuery(
                any(),
                any(),
                any(),
                any()
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
}