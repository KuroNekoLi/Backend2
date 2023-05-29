package com.cmoney.backend2.crawlsetting.service

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class CrawlSettingWebImplTest {

    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: CrawlSettingService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: CrawlSettingWeb
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = CrawlSettingWebImpl(
            manager = manager,
            service = service,
            gson = gson,
            dispatcherProvider = TestDispatcherProvider()
        )
        coEvery {
            manager.getCrawlSettingSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getCathayCaStatus_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CrawlSettingService/cathaycastatus"
        val urlSlot = slot<String>()
        coEvery {
            service.getCathayCaStatus(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success("")
        web.getCathayCaStatus(
            userInfoKey = "123"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCathayCaStatus_success(): Unit = testScope.runTest {
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
    fun getCathayCaStatus_401_UNAUTHORIZATION(): Unit = testScope.runTest {
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
    fun `getTaishinCaStatus_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CrawlSettingService/taishincastatus"
        val urlSlot = slot<String>()
        coEvery {
            service.getTaishinCaStatus(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success("")
        web.getTaishinCaStatus(
            userInfoKey = "123"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTaishinCaStatus_success(): Unit = testScope.runTest {
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
    fun getTaishinCaStatus_401_UNAUTHORIZATION(): Unit = testScope.runTest {
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

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }

}
