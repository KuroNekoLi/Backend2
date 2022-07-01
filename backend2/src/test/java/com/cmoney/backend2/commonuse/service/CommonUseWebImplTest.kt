package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class CommonUseWebImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: CommonUseService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var web: CommonUseWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = CommonUseWebImpl(
            "",
            service,
            setting,
            gson,
            dispatcherProvider = TestDispatcher()
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getRemoteConfigLabel with non null response`() = mainCoroutineRule.runBlockingTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "remoteConfigLabel": "A"
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(any(), any(), any())
        } returns Response.success(response.asJsonObject)

        val result = web.getRemoteConfigLabel()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo("A")
    }

    @Test
    fun `getRemoteConfigLabel with null response`() = mainCoroutineRule.runBlockingTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "remoteConfigLabel": null
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(any(), any(), any())
        } returns Response.success(response.asJsonObject)

        val result = web.getRemoteConfigLabel()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEmpty()
    }
}
