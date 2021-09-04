package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: UserBehaviorService
    private lateinit var web: UserBehaviorWeb
    private val gson = GsonBuilder().serializeNulls().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = UserBehaviorWebImpl(
            gson = gson,
            service = service,
            setting = TestSetting(),
            dispatcher = TestDispatcher()
        )
    }

    @Test(expected = ServerException::class)
    fun `uploadReport_失敗`() = mainCoroutineRule.runBlockingTest {
        val error = CMoneyError(detail = CMoneyError.Detail(400, "error"))
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.uploadReport(
                authorization = any(), requestBody = any()
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

    @Test
    fun `uploadReport_成功`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.uploadReport(
                authorization = any(), requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = web.uploadReport(
            events = listOf(),
            processId = "",
            appId = 0,
            platform = 0,
            version = "",
            os = "",
            device = ""
        ).getOrThrow()
        Truth.assertThat(result).isEqualTo(Unit)
    }
}