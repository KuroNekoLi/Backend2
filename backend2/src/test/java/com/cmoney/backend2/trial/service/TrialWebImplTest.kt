package com.cmoney.backend2.trial.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.trial.service.api.checktrialtime.CheckTrialTimeResponseBody
import com.cmoney.backend2.trial.service.api.gettrialquota.GetTrialQuotaResponseBody
import com.cmoney.backend2.trial.service.api.setquotause.SetQuotaUseResponseBody
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
class TrialWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: TrialService
    private lateinit var trialWeb: TrialWeb
    private val mediaType = "application/json".toMediaType()
    private val gson = GsonBuilder().serializeNulls().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        trialWeb = TrialWebImpl(
            manager = manager,
            trialService = service,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getTrialSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `setQuotaUsageUse_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}Authentication/trial-quota/usage/use"
        val urlSlot = slot<String>()
        val responseBody = SetQuotaUseResponseBody(hasTrialAuth = true)
        coEvery {
            service.setQuotaUsageUse(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success<SetQuotaUseResponseBody>(200, responseBody)

        trialWeb.setQuotaUsageUse(trialKey = "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setQuotaUsageUse_success_hasTrialAuth_true() = testScope.runTest {
        val responseBody =
            SetQuotaUseResponseBody(
                true
            )
        coEvery {
            service.setQuotaUsageUse(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success<SetQuotaUseResponseBody>(200, responseBody)

        val result = trialWeb.setQuotaUsageUse(trialKey = "")
        Truth.assertThat(result.getOrNull()?.hasTrialAuth).isTrue()
    }

    @Test
    fun setQuotaUsageUse_success_hasTrialAuth_false() = testScope.runTest {
        val responseBody =
            SetQuotaUseResponseBody(
                false
            )
        coEvery {
            service.setQuotaUsageUse(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success<SetQuotaUseResponseBody>(200, responseBody)

        val result = trialWeb.setQuotaUsageUse(trialKey = "")
        Truth.assertThat(result.getOrNull()?.hasTrialAuth).isFalse()
    }

    @Test(expected = TimeoutException::class)
    fun setQuotaUsageUse_failure_TimeoutException() = testScope.runTest {
        coEvery {
            service.setQuotaUsageUse(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } answers {
            throw TimeoutException()
        }

        val result = trialWeb.setQuotaUsageUse(
            trialKey = ""
        )
        result.getOrThrow()
    }

    @Test
    fun setQuotaUsageUse_failure_ServerException() = testScope.runTest {
        val errorJson = "{\"error\": {\"code\": 400,\"message\": \"無效的試用金鑰\"}}"
        val responseBody = errorJson.toResponseBody(mediaType)
        coEvery {
            service.setQuotaUsageUse(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.error<SetQuotaUseResponseBody>(400, responseBody)

        val result = trialWeb.setQuotaUsageUse(trialKey = "")
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(400)
        Truth.assertThat(exception.message).contains("無效的試用金鑰")
    }

    @Test
    fun `setQuotaTimeUse_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}Authentication/trial-quota/time/use"
        val urlSlot = slot<String>()
        val responseBody = SetQuotaUseResponseBody(hasTrialAuth = true)
        coEvery {
            service.setQuotaTimeUse(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any(),
                quotaUsed = any()
            )
        } returns Response.success(responseBody)

        trialWeb.setQuotaTimeUse(trialKey = "", quotaUsed = 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setQuotaTimeUse_success() = testScope.runTest {
        val responseBody = SetQuotaUseResponseBody(hasTrialAuth = true)
        coEvery {
            service.setQuotaTimeUse(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any(),
                quotaUsed = any()
            )
        } returns Response.success(responseBody)

        val result = trialWeb.setQuotaTimeUse(trialKey = "", quotaUsed = 20)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.hasTrialAuth).isTrue()
    }

    @Test
    fun setQuotaTimeUse_failure_ServerException() = testScope.runTest {
        coEvery {
            service.setQuotaTimeUse(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any(),
                quotaUsed = any()
            )
        } returns Response.error(
            400,
            """{
            "Error": {
              "Code": 100,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = trialWeb.setQuotaTimeUse(trialKey = "", quotaUsed = 20)
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
        Truth.assertThat(exception.message).isEqualTo("參數錯誤")
    }

    @Test
    fun `checkTrialTime_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}Authentication/trial-time/check"
        val urlSlot = slot<String>()
        val responseBody = CheckTrialTimeResponseBody(timeRemaining = null)
        coEvery {
            service.checkTrialTime(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success(responseBody)

        trialWeb.checkTrialTime(trialKey = "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkTrialTime_success() = testScope.runTest {
        val responseBody = CheckTrialTimeResponseBody(timeRemaining = 100)
        coEvery {
            service.checkTrialTime(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success(responseBody)

        val result = trialWeb.checkTrialTime(trialKey = "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.timeRemaining).isEqualTo(100)
    }

    @Test
    fun checkTrialTime_failure_ServerException() = testScope.runTest {
        coEvery {
            service.checkTrialTime(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.error(
            400,
            """{
            "Error": {
              "Code": 100,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = trialWeb.checkTrialTime(trialKey = "")
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
        Truth.assertThat(exception.message).isEqualTo("參數錯誤")
    }

    @Test
    fun `getQuotaTime_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}Authentication/trial-quota/get"
        val urlSlot = slot<String>()
        val responseBody = GetTrialQuotaResponseBody(quota = 10)
        coEvery {
            service.getTrialQuota(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success(responseBody)

        trialWeb.getQuotaTime(trialKey = "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getQuotaTime_success() = testScope.runTest {
        val responseBody = GetTrialQuotaResponseBody(quota = 10)
        coEvery {
            service.getTrialQuota(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.success(responseBody)

        val result = trialWeb.getQuotaTime(trialKey = "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.quota).isEqualTo(10)
    }

    @Test
    fun getQuotaTime_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getTrialQuota(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                trialKey = any()
            )
        } returns Response.error(
            400,
            """{
            "Error": {
              "Code": 100,
                "Message": "參數錯誤"
              }
            }""".toResponseBody()
        )

        val result = trialWeb.getQuotaTime(trialKey = "")
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
        Truth.assertThat(exception.message).isEqualTo("參數錯誤")
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}