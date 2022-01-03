package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigResponseBody
import com.cmoney.backend2.clientconfiguration.service.api.ConfigKey
import com.google.common.truth.Truth
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private val clientConfigurationService = mockk<ClientConfigurationService>()
    private val gson = Gson()
    private lateinit var clientConfigurationWeb: ClientConfigurationWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        clientConfigurationWeb =
            ClientConfigurationWebImpl(clientConfigurationService, gson, TestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getConfig_取得設定檔成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            clientConfigurationService.getConfig(keys = any())
        } returns Response.success(ClientConfigResponseBody(listOf()))

        val result = clientConfigurationWeb.getConfig(listOf(ConfigKey.KOL))
        Truth.assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getConfig_取得設定檔失敗測試`() = mainCoroutineRule.runBlockingTest {
        val json = ""
        coEvery {
            clientConfigurationService.getConfig(keys = any())
        } returns Response.error(401, json.toResponseBody())

        val result = clientConfigurationWeb.getConfig(listOf(ConfigKey.KOL))
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as HttpException
        Truth.assertThat(exception).isNotNull()
    }
}