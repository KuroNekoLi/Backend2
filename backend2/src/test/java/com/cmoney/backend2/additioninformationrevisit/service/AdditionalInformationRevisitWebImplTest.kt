package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.additioninformationrevisit.service.testing.CandleChartRequest
import com.cmoney.backend2.additioninformationrevisit.service.testing.SomeTickRequest
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class AdditionalInformationRevisitWebImplTest : KoinTest {
    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var webImpl: AdditionalInformationRevisitWeb
    private lateinit var service: AdditionalInformationRevisitService
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .create()
        service = mockk()
        webImpl = AdditionalInformationRevisitWebImpl(
            setting = TestSetting(),
            service = service,
            dispatcher = TestDispatcher()
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAll_success_ListListString() = mainCoroutineRule.runBlockingTest {
        coEvery { service.getAll(any(), any(), any(), any(), any()) } returns Response.success(
            emptyList()
        )
        val typeName = "StockCalculation"
        val response = webImpl.getAll(listOf(), typeName, emptyList())
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTarget_success_ListListString() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getTarget(
                any(),
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
        val response = webImpl.getTarget(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("Commodity", "CommKey"),
            value = gson.toJson(listOf("2330")),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMultiple_success_ListListString() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getMultiple(
                any(),
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
        val response = webImpl.getMultiple(
            typeName = typeName,
            columns = listOf(),
            keyNamePath = listOf("傳輸序號", "標的"),
            value = gson.toJson(CandleChartRequest("2330", 1)),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getOtherQuery_success_ListListString() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getOtherQuery(
                any(),
                any(),
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
        val response = webImpl.getOtherQuery(
            requestType = "SectionTransactionDetailsRequest",
            responseType = responseType,
            columns = columns,
            value = gson.toJson(SomeTickRequest("2330", 0, 10)),
            processSteps = emptyList()
        )
        Truth.assertThat(response.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSignal_success_ListListString() = mainCoroutineRule.runBlockingTest {
        coEvery { service.getSignal(any(), any(), any(), any()) } returns Response.success(
            listOf(
                listOf("2330", "1620696741000", "true", "true", "true")
            )
        )
        val channels = listOf<String>("4218074", "4217863", "4218054")
        val response = webImpl.getSignal(channels)
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
}