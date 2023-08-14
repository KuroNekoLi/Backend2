package com.cmoney.backend2.frontendlogger.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class FrontEndLoggerWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: FrontEndLoggerService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: FrontEndLoggerWebImpl

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = FrontEndLoggerWebImpl(
            manager = manager,
            service = service,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getFrontEndLoggerSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `log_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}frontendlogger/log/default"
        val urlSlot = slot<String>()
        coEvery {
            service.log(
                url = capture(urlSlot),
                authToken = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)

        web.log(body = emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `log_check url when index change`() = testScope.runTest {
        val changeIndex = "change"
        val expect = "${EXCEPT_DOMAIN}frontendlogger/log/$changeIndex"
        val urlSlot = slot<String>()
        coEvery {
            service.log(
                url = capture(urlSlot),
                authToken = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)

        web.log(indexName = changeIndex, body = emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `log 成功`() = testScope.runTest {
        coEvery {
            service.log(any(), any(), any())
        } returns Response.success<Void>(204, null)

        val result = web.log(body = emptyList())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `log 身分驗證錯誤`() = testScope.runTest {
        coEvery {
            service.log(any(), any(), any())
        } returns Response.error(401, "".toResponseBody())

        val result = web.log(body = emptyList())
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        exception!!
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        Truth.assertThat((exception as HttpException).code()).isEqualTo(401)
    }

    @Test
    fun `log indexName錯誤`() = testScope.runTest {
        coEvery {
            service.log(any(), any(), any())
        } returns Response.error(404, "".toResponseBody())

        val result = web.log(body = emptyList())
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        exception!!
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        Truth.assertThat((exception as HttpException).code()).isEqualTo(404)
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80"
    }
}
