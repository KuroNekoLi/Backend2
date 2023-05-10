package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ClientConfigurationWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val clientConfigurationService = mockk<ClientConfigurationService>()
    private val gson = Gson()
    private lateinit var clientConfigurationWeb: ClientConfigurationWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        clientConfigurationWeb = ClientConfigurationWebImpl(
            manager = manager,
            service = clientConfigurationService,
            jsonParser = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getClientConfigurationSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getConfig_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ClientConfiguration/api/config/get"
        val urlSlot = slot<String>()
        coEvery {
            clientConfigurationService.getConfig(
                url = capture(urlSlot),
                keys = any()
            )
        } returns Response.success(ClientConfigResponseBody(listOf()))

        clientConfigurationWeb.getConfig(listOf())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getConfig_取得設定檔成功測試`() = testScope.runTest {
        coEvery {
            clientConfigurationService.getConfig(
                url = any(),
                keys = any()
            )
        } returns Response.success(ClientConfigResponseBody(listOf()))

        val result = clientConfigurationWeb.getConfig(listOf())
        Truth.assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getConfig_取得設定檔失敗測試 HttpException`() = testScope.runTest {
        val json = ""
        coEvery {
            clientConfigurationService.getConfig(
                url = any(),
                keys = any()
            )
        } returns Response.error(401, json.toResponseBody())

        val result = clientConfigurationWeb.getConfig(listOf())
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}