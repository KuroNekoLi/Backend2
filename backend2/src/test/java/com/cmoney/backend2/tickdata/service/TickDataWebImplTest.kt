package com.cmoney.backend2.tickdata.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.tickdata.service.api.getkchartdata.KDataItem
import com.cmoney.backend2.tickdata.service.api.getmachartdata.MaDataItem
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.MultipleMovingAverageData
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class TickDataWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @MockK
    private lateinit var service: TickDataService
    private lateinit var tickDataWeb: TickDataWeb
    private val gson = GsonBuilder().serializeNulls().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tickDataWeb = TickDataWebImpl(
            manager = manager,
            tickDataService = service,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getTickDataSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getKChartData_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}TickDataService/api/GetKChartData"
        val urlSlot = slot<String>()
        val origin = getDataText(fileName = "tickdata/KChartData.txt")
        val data = gson.fromJson<List<KDataItem>>(
            origin,
            object: TypeToken<List<KDataItem>>(){}.type
        )
        coEvery {
            service.getKChartData(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(data)

        tickDataWeb.getKChartData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getKChartData_success() = testScope.runTest {
        val origin = getDataText(fileName = "tickdata/KChartData.txt")
        val data = gson.fromJson<List<KDataItem>>(
            origin,
            object: TypeToken<List<KDataItem>>(){}.type
        )
        coEvery {
            service.getKChartData(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(data)

        val result = tickDataWeb.getKChartData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val responseData = result.getOrThrow()
        Truth.assertThat(responseData).isEqualTo(data)
    }

    @Test
    fun getKChartData_failure() = testScope.runTest {
        val error = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }
        """.trimIndent()
        coEvery {
            service.getKChartData(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, error.toResponseBody())

        val result = tickDataWeb.getKChartData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        )
        Truth.assertThat(result.isSuccess).isFalse()
        checkServerException(result)
    }

    @Test
    fun `getMAData_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}TickDataService/api/GetMAChartData"
        val urlSlot = slot<String>()
        val origin = getDataText(fileName = "tickdata/MAChartData.txt")
        val data = gson.fromJson<List<MaDataItem>>(
            origin,
            object: TypeToken<List<MaDataItem>>(){}.type
        )
        coEvery {
            service.getMAChartData(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(data)

        tickDataWeb.getMAData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMAData_success() = testScope.runTest {
        val origin = getDataText(fileName = "tickdata/MAChartData.txt")
        val data = gson.fromJson<List<MaDataItem>>(
            origin,
            object: TypeToken<List<MaDataItem>>(){}.type
        )
        coEvery {
            service.getMAChartData(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(data)

        val result = tickDataWeb.getMAData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val responseData = result.getOrThrow()
        Truth.assertThat(responseData).isEqualTo(data)
    }

    @Test
    fun getMAData_failure() = testScope.runTest {
        val error = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }
        """.trimIndent()
        coEvery {
            service.getMAChartData(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, error.toResponseBody())

        val result = tickDataWeb.getMAData(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5
        )
        Truth.assertThat(result.isSuccess).isFalse()
        checkServerException(result)
    }

    @Test
    fun `getMultipleMovingAverage_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}TickDataService/api/GetMultipleMovingAverage"
        val urlSlot = slot<String>()
        val origin = getDataText(fileName = "tickdata/MultipleMovingAverage.txt")
        val data = gson.fromJson<List<MultipleMovingAverageData>>(
            origin,
            object: TypeToken<List<MultipleMovingAverageData>>(){}.type
        )
        coEvery {
            service.getMultipleMovingAverage(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(data)

        tickDataWeb.getMultipleMovingAverage(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5,
            dataPoints = listOf(1, 5, 30, 60)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMultipleMovingAverage_success() = testScope.runTest {
        val origin = getDataText(fileName = "tickdata/MAChartData.txt")
        val data = gson.fromJson<List<MultipleMovingAverageData>>(
            origin,
            object: TypeToken<List<MultipleMovingAverageData>>(){}.type
        )
        coEvery {
            service.getMultipleMovingAverage(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(data)

        val result = tickDataWeb.getMultipleMovingAverage(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5,
            dataPoints = listOf(1, 5, 30, 60)
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val responseData = result.getOrThrow()
        Truth.assertThat(responseData).isEqualTo(data)
    }

    @Test
    fun getMultipleMovingAverage_failure() = testScope.runTest {
        val error = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }
        """.trimIndent()
        coEvery {
            service.getMultipleMovingAverage(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, error.toResponseBody())

        val result = tickDataWeb.getMultipleMovingAverage(
            date = 1606752000000L,
            commKey = "TXF1",
            minuteInterval = 30,
            count = 5,
            dataPoints = listOf(1, 5, 30, 60)
        )
        Truth.assertThat(result.isSuccess).isFalse()
        checkServerException(result)
    }

    private fun getDataText(fileName: String): String {
        return context.assets.open(fileName).use {
            it.bufferedReader().readText()
        }
    }

    private fun checkServerException(result: Result<*>) {
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
        Truth.assertThat(exception.message).isEqualTo("參數錯誤")
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}