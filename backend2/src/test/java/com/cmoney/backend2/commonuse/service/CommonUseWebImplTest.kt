package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreference
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import okhttp3.ResponseBody.Companion.toResponseBody
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
    val mainCoroutineRule = CoroutineTestRule()

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
    fun `getRemoteConfigLabel with non null response`() = mainCoroutineRule.runTest {
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
    fun `getRemoteConfigLabel with null response`() = mainCoroutineRule.runTest {
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

    @Test
    fun `updateInvestmentPreference success`() = mainCoroutineRule.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "updateMember": {
                      "updateInvestmentRiskPreference": [
                        1,
                        2,
                        3
                      ]
                    }
                  }
                }
            """.trimMargin()
        )

        coEvery {
            service.query(any(), any(), any())
        } returns Response.success(response.asJsonObject)

        val result =
            web.updateInvestmentPreference(investmentPreferenceType = InvestmentPreferenceType.All)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(InvestmentPreferenceType.All)
    }

    @Test
    fun `updateInvestmentPreference failure`() = mainCoroutineRule.runTest {
        coEvery {
            service.query(any(), any(), any())
        } returns Response.error(400, "".toResponseBody())

        val result =
            web.updateInvestmentPreference(investmentPreferenceType = InvestmentPreferenceType.All)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getInvestmentPreference success`() = mainCoroutineRule.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "investmentRiskPreferences": [
                        {
                          "id": 1,
                          "name": "短線賺價差",
                          "isChosen": true
                        }
                      ]
                    }
                  }
                }
            """.trimMargin()
        )
        val responseBody = listOf(
            InvestmentPreference(
                id = 1,
                name = "短線賺價差",
                isChosen = true
            )
        )

        coEvery {
            service.query(any(), any(), any())
        } returns Response.success(response.asJsonObject)

        val result = web.getInvestmentPreferences()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(responseBody)
    }

    @Test
    fun `gegInvestmentPreference failure`() = mainCoroutineRule.runTest {
        coEvery {
            service.query(any(), any(), any())
        } returns Response.error(400, "".toResponseBody())

        val result = web.getInvestmentPreferences()
        Truth.assertThat(result.isSuccess).isFalse()
    }
}
