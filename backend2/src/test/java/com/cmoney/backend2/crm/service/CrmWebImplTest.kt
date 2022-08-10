package com.cmoney.backend2.crm.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.crm.service.api.creatlivechat.CreateLiveChatResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CrmWebImplTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var service: CrmService
    private lateinit var crmWeb: CrmWeb
    private lateinit var setting: Setting
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        crmWeb = CrmWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `createLiveChat_成功`() = mainCoroutineRule.runTest {
        val expect =
            "https://cmoney.01.firstline.cc/livechat/messenger/13?once_code=be99ffdf-75da-43cd-aa10-366620692211"

        val responseBody = CreateLiveChatResponseBody(
            liveChatUrl = "https://cmoney.01.firstline.cc/livechat/messenger/13?once_code=be99ffdf-75da-43cd-aa10-366620692211"
        )
        coEvery {
            service.createLiveChat(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = crmWeb.createLiveChat(true)
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun `createLiveChat_失敗`() = mainCoroutineRule.runTest {
        val errorBody = gson.toJson(CMoneyError(message = "錯誤訊息")).toResponseBody()
        coEvery {
            service.createLiveChat(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = crmWeb.createLiveChat(true)
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("錯誤訊息")
    }
}