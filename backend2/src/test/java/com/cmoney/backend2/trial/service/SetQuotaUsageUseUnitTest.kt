package com.cmoney.backend2.trial.service

import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeoutException

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class SetQuotaUsageUseUnitTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: TrialService
    private lateinit var trialWeb: TrialWeb
    private lateinit var apiParam: MemberApiParam
    private val mediaType = "application/json".toMediaType()
    private val gson = GsonBuilder().serializeNulls().create()

    @Before
    fun setUp() {
        apiParam = MemberApiParam(99, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        MockKAnnotations.init(this)
        trialWeb = TrialWebImpl(gson, TestSetting(), service, TestDispatcherProvider())
    }

    @Test(expected = TimeoutException::class)
    fun setQuotaUsageUse_TimeoutException() = testScope.runTest {
        coEvery {
            service.setQuotaUsageUse(
                appId = any(),
                authorization = any(),
                guid = any(),
                trialKey = any()
            )
        } answers {
            throw TimeoutException()
        }

        val result = trialWeb.setQuotaUsageUse(
            apiParam,
            ""
        )
        result.getOrThrow()
    }

    @Test
    fun `setQuotaUsageUse_失敗_無效的金鑰`() = testScope.runTest {
        val errorJson = "{\"error\": {\"code\": 400,\"message\": \"無效的試用金鑰\"}}"
        val responseBody = errorJson.toResponseBody(mediaType)
        coEvery {
            service.setQuotaUsageUse(
                appId = any(),
                authorization = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.error<SetQuotaUseResponseBody>(400, responseBody)

        val result = trialWeb.setQuotaUsageUse(apiParam, "")
        Truth.assertThat(result.exceptionOrNull()?.message).contains("無效的試用金鑰")
    }

    @Test
    fun `setQuotaUsageUse_成功_hasTrialAuth為true`() = testScope.runTest {
        val responseBody =
            SetQuotaUseResponseBody(
                true
            )
        coEvery {
            service.setQuotaUsageUse(
                appId = any(),
                authorization = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success<SetQuotaUseResponseBody>(200, responseBody)

        val result = trialWeb.setQuotaUsageUse(apiParam, "")
        Truth.assertThat(result.getOrNull()?.hasTrialAuth).isTrue()
    }

    @Test
    fun `setQuotaUsageUse_成功_hasTrialAuth為false`() = testScope.runTest {
        val responseBody =
            SetQuotaUseResponseBody(
                false
            )
        coEvery {
            service.setQuotaUsageUse(
                appId = any(),
                authorization = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success<SetQuotaUseResponseBody>(200, responseBody)

        val result = trialWeb.setQuotaUsageUse(apiParam, "")
        Truth.assertThat(result.getOrNull()?.hasTrialAuth).isFalse()
    }
}