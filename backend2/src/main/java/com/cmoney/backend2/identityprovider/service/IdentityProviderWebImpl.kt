package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBody
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class IdentityProviderWebImpl(
    private val service: IdentityProviderService,
    private val gson: Gson,
    private val setting: Setting,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : IdentityProviderWeb {

    override suspend fun isTokenLatest(): Result<Boolean> = withContext(dispatcherProvider.io()) {
        runCatching {
            val responseBody = service.isTokenLatest(
                accessToken = setting.accessToken.createAuthorizationBearer()
            ).checkResponseBody(gson)
                .toRealResponse()
            return@runCatching responseBody.isSuccess
        }
    }

    override suspend fun loginByEmail(
        account: String,
        hashedPassword: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val xApiLog = XApiLog(
                appId = setting.appId,
                platform = setting.platform.code,
                mode = 1
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                xApiLog = xApiLog,
                grantType = "password",
                clientId = setting.clientId,
                account = account,
                hashedPassword = hashedPassword,
                loginMethod = "email"
            ).checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByCellphone(
        cellphone: String,
        hashedPassword: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        kotlin.runCatching {
            val xApiLog = XApiLog(
                appId = setting.appId,
                platform = setting.platform.code,
                mode = 3
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                xApiLog = xApiLog,
                grantType = "password",
                clientId = setting.clientId,
                account = cellphone,
                hashedPassword = hashedPassword,
                loginMethod = "cellphone"
            ).checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByFacebook(accessToken: String): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = setting.appId,
                    platform = setting.platform.code,
                    mode = 2
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    xApiLog = xApiLog,
                    grantType = "token-exchange",
                    clientId = setting.clientId,
                    providerToken = accessToken,
                    provider = "facebook"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun loginByGoogle(accessToken: String): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = setting.appId,
                    platform = setting.platform.code,
                    mode = 7
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    xApiLog = xApiLog,
                    grantType = "token-exchange",
                    clientId = setting.clientId,
                    providerToken = accessToken,
                    provider = "google"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun loginByFirebaseAnonymousToken(anonymousToken: String): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = setting.appId,
                    platform = setting.platform.code,
                    mode = 5
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    xApiLog = xApiLog,
                    grantType = "token-exchange",
                    clientId = setting.clientId,
                    providerToken = anonymousToken,
                    provider = "guest"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun loginByPkce(
        redirectUri: String,
        codeVerifier: String,
        code: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        kotlin.runCatching {
            val xApiLog = XApiLog(
                appId = setting.appId,
                platform = setting.platform.code,
                mode = 9
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                xApiLog = xApiLog,
                grantType = "authorization_code",
                clientId = setting.clientId,
                redirectUri = redirectUri,
                code = code,
                codeVerifier = codeVerifier
            )
                .checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByCMoneyThirdParty(providerToken: String): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val xApiLog = XApiLog(
                appId = setting.appId,
                platform = setting.platform.code,
                mode = 8
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                xApiLog = xApiLog,
                grantType = "token-exchange",
                clientId = setting.clientId,
                providerToken = providerToken,
                provider = "cmoneythirdparty"
            )
                .checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = setting.appId,
                    platform = setting.platform.code,
                    mode = 6
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    xApiLog = xApiLog,
                    grantType = "refresh_token",
                    clientId = setting.clientId,
                    refreshToken = refreshToken
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun revokeToken(
        token: String
    ): Result<RevokeResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                service.revokeIdentityToken(
                    accessToken = setting.accessToken.createAuthorizationBearer(),
                    clientId = setting.clientId,
                    token = token,
                    tokenType = "refresh_token"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }
}