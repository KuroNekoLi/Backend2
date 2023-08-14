package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
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

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class UserBehaviorRemoteDataSourceTest {
    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: UserBehaviorService
    private lateinit var web: UserBehaviorWeb
    private val gson = GsonBuilder().serializeNulls().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = UserBehaviorWebImpl(
            manager = manager,
            gson = gson,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getUserBehaviorSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `uploadReport_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}UserBehaviorLog/Log"
        val urlSlot = slot<String>()
        coEvery {
            service.uploadReport(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)
        web.uploadReport(
            events = listOf(),
            processId = null,
            appId = 0,
            platform = 0,
            version = "",
            os = null,
            device = null
        ).getOrThrow()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun uploadReport_success() = testScope.runTest {
        coEvery {
            service.uploadReport(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = web.uploadReport(
            events = listOf(),
            processId = null,
            appId = 0,
            platform = 0,
            version = "",
            os = null,
            device = null
        ).getOrThrow()
        Truth.assertThat(result).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun uploadReport_failure_ServerException() = testScope.runTest {
        val error = CMoneyError(detail = CMoneyError.Detail(400, "error"))
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.uploadReport(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, responseBody)

        val result = web.uploadReport(
            events = listOf(),
            processId = "",
            appId = 0,
            platform = 0,
            version = "",
            os = "",
            device = ""
        )
        result.getOrThrow()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}