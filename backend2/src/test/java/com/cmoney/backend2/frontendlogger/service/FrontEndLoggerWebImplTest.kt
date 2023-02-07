package com.cmoney.backend2.frontendlogger.service

import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: FrontEndLoggerService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: FrontEndLoggerWebImpl
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = FrontEndLoggerWebImpl(
            baseHost = "",
            service = service,
            setting = setting,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
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

}
