package com.cmoney.backend2.authorization.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.model.exception.EmptyBodyException
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
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

    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    lateinit var service: AuthorizationService

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var authorizationWeb: AuthorizationWeb

    companion object {
        private const val HAS_AUTH_RESPONSE = "authorization_get_expire_time_has_auth_response.txt"
        private const val NON_AUTH_RESPONSE = "authorization_get_expire_time_non_auth_response.txt"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authorizationWeb = AuthorizationWebImpl(
            gson = gson,
            service = service,
            manager = manager,
            dispatcher = TestDispatcherProvider()
        )
    }

    @Test
    fun `getExpiredTime_有權限_成功`() = testScope.runTest {
        val expiredTimeJson = context.assets.open(HAS_AUTH_RESPONSE)
            .bufferedReader()
            .use {
                it.readText()
            }
        val responseBody = gson.fromJson(expiredTimeJson, ExpiredTime::class.java)

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                url = any()
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
    fun `getExpiredTime_無權限_成功`() = testScope.runTest {
        val expiredTimeJson = context.assets.open(NON_AUTH_RESPONSE)
            .bufferedReader()
            .use {
                it.readText()
            }

        val responseBody = gson.fromJson(expiredTimeJson, ExpiredTime::class.java)

        coEvery {
            service.getExpiredTime(
                authorization = any(),
                url = any()
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
    fun `getExpiredTime_200_失敗_EmptyBodyException`() = testScope.runTest {
        coEvery {
            service.getExpiredTime(
                authorization = any(),
                url = any()
            )
        } returns Response.success<ExpiredTime>(200, null)

        val result = authorizationWeb.getExpiredTime(type = Type.MOBILE_PAID, subjectId = 0)
        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        val exception = requireNotNull(result.exceptionOrNull())
        Truth.assertThat(exception).isInstanceOf(EmptyBodyException::class.java)
    }

    @Test
    fun `getExpiredTime_400_失敗`() = testScope.runTest {
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
                url = any()
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
    fun `getExpiredTime_401_失敗`() = testScope.runTest {
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
                url = any()
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
    fun `getExpiredTime_500_失敗`() = testScope.runTest {
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
                url = any()
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
    fun `hasAuth_200_成功`() = testScope.runTest {
        val auth = Auth(hasAuthorization = true)
        coEvery {
            service.hasAuth(authorization = any(), url = any())
        } returns Response.success(auth)
        val result = authorizationWeb.hasAuth(type = Type.MOBILE_PAID, subjectId = 0)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrNull()
        requireNotNull(data)
        Truth.assertThat(data.hasAuthorization).isTrue()
    }

    @Test
    fun hasAuth_401_失敗() = testScope.runTest {
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

    @Test
    fun getPurchasedSubjectIds_success() = testScope.runTest {
        coEvery {
            service.getPurchasedSubjectIds(authorization = any(), url = any())
        } returns Response.success(listOf())
        val result = authorizationWeb.getPurchasedSubjectIds(type = Type.MOBILE_PAID)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPurchasedSubjectIds_failure() = testScope.runTest {
        coEvery {
            service.getPurchasedSubjectIds(authorization = any(), url = any())
        } returns Response.error(400, "".toResponseBody())
        val result = authorizationWeb.getPurchasedSubjectIds(type = Type.MOBILE_PAID)
        Truth.assertThat(result.isSuccess).isFalse()
    }
}
