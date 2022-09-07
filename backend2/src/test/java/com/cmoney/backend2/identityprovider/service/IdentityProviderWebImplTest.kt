package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBodyWithError
import com.cmoney.backend2.identityprovider.service.api.islatest.IsLatestResponseBodyWithError
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBodyWithError
import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth.assertThat
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
import org.robolectric.annotation.Config
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class IdentityProviderWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: IdentityProviderService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var web: IdentityProviderWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = IdentityProviderWebImpl(
            service = service,
            gson = gson,
            setting = setting,
            dispatcherProvider = TestDispatcher()
        )
    }

    @Test
    fun `isTokenLatest_是最新回傳true`() = testScope.runTest {
        val responseBody = IsLatestResponseBodyWithError(isSuccess = true)
        coEvery {
            service.isTokenLatest(any())
        } returns Response.success(responseBody)
        val result = web.isTokenLatest()
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isTrue()
    }

    @Test
    fun `isTokenLatest_是最新回傳false`() = testScope.runTest {
        val responseBody = IsLatestResponseBodyWithError(isSuccess = false)
        coEvery {
            service.isTokenLatest(any())
        } returns Response.success(responseBody)
        val result = web.isTokenLatest()
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isFalse()
    }

    @Test(expected = ServerException::class)
    fun `isTokenLatest_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.isTokenLatest(any())
        } returns Response.error(400, errorBody)
        val result = web.isTokenLatest()
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `isTokenLatest_status is 401_HttpException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.isTokenLatest(any())
        } returns Response.error(401, errorBody)
        val result = web.isTokenLatest()
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `loginByEmail_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByEmail("", "")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `loginByEmail_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.loginByEmail("", "")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `loginByEmail_status is 400_HttpException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.loginByEmail("", "")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `loginByCellphone_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByCellphone("", "")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `loginByCellphone_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.loginByCellphone("", "")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `loginByCellphone_status is 400_HttpException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.loginByCellphone("", "")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `loginByFacebook_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByFacebook("")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `loginByFacebook_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.loginByFacebook("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `loginByFacebook_status is 400_HttpException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.loginByFacebook("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `loginByGoogle_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByGoogle("")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }


    @Test(expected = ServerException::class)
    fun `loginByGoogle_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.loginByGoogle("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `loginByFirebaseAnonymousToken_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByFirebaseAnonymousToken("")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `loginByFirebaseAnonymousToken_status is 400_ServerException`() =
        testScope.runTest {
            val errorBody = gson.toJson(
                CMoneyError(
                    detail = CMoneyError.Detail(
                        code = 101
                    )
                )
            ).toResponseBody()
            coEvery {
                service.getIdentityToken(
                    xApiLog = any(),
                    grantType = any(),
                    clientId = any(),
                    scope = any(),
                    clientSecret = any(),
                    account = any(),
                    hashedPassword = any(),
                    providerToken = any(),
                    provider = any(),
                    loginMethod = any(),
                    code = any(),
                    redirectUri = any(),
                    refreshToken = any()
                )
            } returns Response.error(400, errorBody)
            val result = web.loginByFirebaseAnonymousToken("")
            assertThat(result.isSuccess).isFalse()
            result.getOrThrow()
        }

    @Test(expected = HttpException::class)
    fun `loginByFirebaseAnonymousToken_status is 400_HttpException`() =
        testScope.runTest {
            val errorBody = gson.toJson(
                CMoneyError(
                    detail = CMoneyError.Detail(
                        code = 101
                    )
                )
            ).toResponseBody()
            coEvery {
                service.getIdentityToken(
                    xApiLog = any(),
                    grantType = any(),
                    clientId = any(),
                    scope = any(),
                    clientSecret = any(),
                    account = any(),
                    hashedPassword = any(),
                    providerToken = any(),
                    provider = any(),
                    loginMethod = any(),
                    code = any(),
                    redirectUri = any(),
                    refreshToken = any()
                )
            } returns Response.error(401, errorBody)
            val result = web.loginByFirebaseAnonymousToken("")
            assertThat(result.isSuccess).isFalse()
            result.getOrThrow()
        }

    @Test
    fun `logByPkce_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any(),
                codeVerifier = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByPkce("", "", "")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `loginByPkce_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                message = "參數錯誤"
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any(),
                codeVerifier = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.loginByPkce("", "", "")
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `loginByPkce_status is 401_HttpException`() = testScope.runTest {
        val errorBody = "".toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any(),
                codeVerifier = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.loginByPkce("", "", "")
        result.getOrThrow()
    }

    @Test
    fun `logByCMoneyThirdParty_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any(),
                codeVerifier = any()
            )
        } returns Response.success(responseBody)
        val result = web.loginByCMoneyThirdParty("")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = HttpException::class)
    fun `logByCMoneyThirdParty is 401_HttpException`() = testScope.runTest {
        val errorBody = "".toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any(),
                codeVerifier = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.loginByCMoneyThirdParty( "")
        result.getOrThrow()
    }

    @Test
    fun `revokeToken_http is 200_success是true`() = testScope.runTest {
        val responseBody = RevokeResponseBodyWithError(tokenType = "", success = true)
        coEvery {
            service.revokeIdentityToken(
                clientId = any(),
                clientSecret = any(),
                token = any(),
                tokenType = any(), accessToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.revokeToken("")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().success).isTrue()
    }

    @Test
    fun `revokeToken_http is 200_success是false`() = testScope.runTest {
        val responseBody = RevokeResponseBodyWithError(tokenType = "", success = false)
        coEvery {
            service.revokeIdentityToken(
                clientId = any(),
                clientSecret = any(),
                token = any(),
                tokenType = any(), accessToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.revokeToken("")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().success).isFalse()
    }

    @Test(expected = ServerException::class)
    fun `revokeToken_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.revokeIdentityToken(
                clientId = any(),
                clientSecret = any(),
                token = any(),
                tokenType = any(), accessToken = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.revokeToken("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `revokeToken_status is 401_HttpException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.revokeIdentityToken(
                clientId = any(),
                clientSecret = any(),
                token = any(),
                tokenType = any(), accessToken = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.revokeToken("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `refreshToken_status is 200_成功`() = testScope.runTest {
        val responseBody = GetTokenResponseBodyWithError(
            accessToken = "",
            expiresIn = 0,
            idToken = "",
            refreshToken = "",
            tokenType = ""
        )
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.success(responseBody)
        val result = web.refreshToken("")
        val resultValue = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(resultValue).isEqualTo(responseBody.toRealResponse())
    }

    @Test(expected = ServerException::class)
    fun `refreshToken_status is 400_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.refreshToken("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `refreshToken_status is 401_HttpException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getIdentityToken(
                xApiLog = any(),
                grantType = any(),
                clientId = any(),
                scope = any(),
                clientSecret = any(),
                account = any(),
                hashedPassword = any(),
                providerToken = any(),
                provider = any(),
                loginMethod = any(),
                code = any(),
                redirectUri = any(),
                refreshToken = any()
            )
        } returns Response.error(401, errorBody)
        val result = web.refreshToken("")
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }
}