package com.cmoney.backend2.crm.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.crm.service.api.creatlivechat.CreateLiveChatResponseBody
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
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CrmWebImplTest {
    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: CrmService
    private lateinit var crmWeb: CrmWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        crmWeb = CrmWebImpl(
            manager = manager,
            gson = gson,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getCrmSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `createLiveChat_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CRM/livechat"
        val urlSlot = slot<String>()
        coEvery {
            service.createLiveChat(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(CreateLiveChatResponseBody(""))
        crmWeb.createLiveChat(isPro = false)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createLiveChat_success() = testScope.runTest {
        val expect =
            "https://cmoney.01.firstline.cc/livechat/messenger/13?once_code=be99ffdf-75da-43cd-aa10-366620692211"

        val responseBody = CreateLiveChatResponseBody(
            liveChatUrl = "https://cmoney.01.firstline.cc/livechat/messenger/13?once_code=be99ffdf-75da-43cd-aa10-366620692211"
        )
        coEvery {
            service.createLiveChat(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = crmWeb.createLiveChat(false)
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun createLiveChat_failure() = testScope.runTest {
        val errorBody = gson.toJson(CMoneyError(message = "錯誤訊息")).toResponseBody()
        coEvery {
            service.createLiveChat(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = crmWeb.createLiveChat(false)
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("錯誤訊息")
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}