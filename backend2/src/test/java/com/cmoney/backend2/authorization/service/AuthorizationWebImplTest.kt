package com.cmoney.backend2.authorization.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
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
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AuthorizationWebImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var service: AuthorizationService

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var authorizationWeb: AuthorizationWeb
    private lateinit var setting: Setting

    companion object {
        private const val HAS_AUTH_RESPONSE = "authorization_get_expire_time_has_auth_response.txt"
        private const val NON_AUTH_RESPONSE = "authorization_get_expire_time_non_auth_response.txt"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        authorizationWeb = AuthorizationWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `getExpiredTime_有權限_成功`() = mainCoroutineRule.runBlockingTest {
        val expiredTimeJson = context.assets.open(HAS_AUTH_RESPONSE)
            .bufferedReader()
            .use {
                it.readText()
            }
        val responseBody = gson.fromJson(expiredTimeJson, ExpiredTime::class.java)

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                type = any(),
                subjectId = any()
            )
        } returns Response.success(responseBody)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0L)

        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        val expiredTime = data.expiredTime
        val serverTime = data.serverTime
        Truth.assertThat(expiredTime).isNotNull()
        Truth.assertThat(serverTime).isNotNull()
        requireNotNull(expiredTime)
        requireNotNull(serverTime)
        Truth.assertThat(serverTime).isLessThan(expiredTime)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }


    @Test
    fun `getExpiredTime_無權限_成功`() = mainCoroutineRule.runBlockingTest {
        val expiredTimeJson = context.assets.open(NON_AUTH_RESPONSE)
            .bufferedReader()
            .use {
                it.readText()
            }

        val responseBody = gson.fromJson(expiredTimeJson, ExpiredTime::class.java)

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                type = any(),
                subjectId = any()
            )
        } returns Response.success(responseBody)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0)

        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        val expiredTime = data.expiredTime
        val serverTime = data.serverTime
        Truth.assertThat(expiredTime).isNotNull()
        Truth.assertThat(serverTime).isNotNull()
        requireNotNull(expiredTime)
        requireNotNull(serverTime)
        Truth.assertThat(expiredTime).isLessThan(serverTime)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }


    @Test
    fun `getExpiredTime_200_失敗_EmptyBodyException`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getExpiredTime(
                authorization = any(),
                type = any(),
                subjectId = any()
            )
        } returns Response.success<ExpiredTime>(200, null)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0)
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        val exception = requireNotNull(result.exceptionOrNull())
        Truth.assertThat(exception).isInstanceOf(EmptyBodyException::class.java)
    }

    @Test
    fun `getExpiredTime_400_失敗`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 400
                )
            )
        ).toResponseBody()

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                type = any(),
                subjectId = any()
            )
        } returns Response.error<ExpiredTime>(400, errorBody)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0)

        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        val exception = requireNotNull(result.exceptionOrNull())
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(400)
    }

    @Test
    fun `getExpiredTime_401_失敗`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 401
                )
            )
        ).toResponseBody()

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                type = any(),
                subjectId = any()
            )
        } returns Response.error<ExpiredTime>(401, errorBody)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0)

        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        val exception = requireNotNull(result.exceptionOrNull())
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getExpiredTime_500_失敗`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 500
                )
            )
        ).toResponseBody()

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                type = any(),
                subjectId = any()
            )
        } returns Response.error<ExpiredTime>(500, errorBody)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0)

        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        val exception = requireNotNull(result.exceptionOrNull())
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(500)
    }

    @Test
    fun `hasAuth_200_成功`() = mainCoroutineRule.runBlockingTest {
        val auth = Auth(hasAuthorization = true, serverTime = null)
        coEvery {
            service.hasAuth(authorization = any(), url = any())
        } returns Response.success(auth)
        val result = authorizationWeb.hasAuth(type = Type.MOBILE_PAID, subjectId = 0)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrNull()
        requireNotNull(data)
        Truth.assertThat(data.hasAuthorization).isTrue()
        Truth.assertThat(data.serverTime).isNull()
    }

    @Test
    fun hasAuth_401_失敗() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 401
                )
            )
        ).toResponseBody()

        coEvery {
            service.hasAuth(
                authorization = any(),
                url = any()
            )
        } returns Response.error<Auth>(401, errorBody)
        val result = authorizationWeb.hasAuth(type = Type.MOBILE_PAID, subjectId = 99)
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        val exception = requireNotNull(result.exceptionOrNull())
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }
}