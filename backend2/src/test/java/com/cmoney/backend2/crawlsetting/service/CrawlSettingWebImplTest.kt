package com.cmoney.backend2.crawlsetting.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class CrawlSettingWebImplTest {

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var service: CrawlSettingService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var web: CrawlSettingWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = CrawlSettingWebImpl(
            baseHost = "",
            setting = setting,
            service = service,
            gson = gson,
            dispatcherProvider = TestDispatcher()
        )
    }

    @Test
    fun getCathayCaStatus_success(): Unit = mainCoroutineRule.runTest {
        val responseBody = "1234"
        coEvery {
            service.getCathayCaStatus(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getCathayCaStatus(
            userInfoKey = "123"
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrNull()
        Truth.assertThat(data).isNotNull()
        requireNotNull(data)
        Truth.assertThat(data).isEqualTo("1234")
    }

    @Test
    fun getCathayCaStatus_401_UNAUTHORIZATION(): Unit = mainCoroutineRule.runTest {
        coEvery {
            service.getCathayCaStatus(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.getCathayCaStatus(
            "123"
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun getTaishinCaStatus_success(): Unit = mainCoroutineRule.runTest {
        val responseBody = "1234"
        coEvery {
            service.getTaishinCaStatus(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getTaishinCaStatus(
            userInfoKey = "123"
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrNull()
        Truth.assertThat(data).isNotNull()
        requireNotNull(data)
        Truth.assertThat(data).isEqualTo("1234")
    }

    @Test
    fun getTaishinCaStatus_401_UNAUTHORIZATION(): Unit = mainCoroutineRule.runTest {
        coEvery {
            service.getTaishinCaStatus(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.getTaishinCaStatus(
            userInfoKey = "123"
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

}
