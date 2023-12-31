package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBody
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class IdentityProviderWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: IdentityProviderService,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : IdentityProviderWeb {

    override suspend fun isTokenLatest(
        domain: String,
        url: String
    ): Result<Boolean> = withContext(dispatcherProvider.io()) {
        runCatching {
            val responseBody = service.isTokenLatest(
                url = url,
                accessToken = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(gson)
                .toRealResponse()
            return@runCatching responseBody.isSuccess
        }
    }

    override suspend fun loginByEmail(
        account: String,
        hashedPassword: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val xApiLog = XApiLog(
                appId = manager.getAppId(),
                platform = manager.getPlatform().code,
                mode = 1
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                url = url,
                xApiLog = xApiLog,
                grantType = "password",
                clientId = manager.getClientId(),
                account = account,
                hashedPassword = hashedPassword,
                loginMethod = "email"
            ).checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByCellphone(
        cellphone: String,
        hashedPassword: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        kotlin.runCatching {
            val xApiLog = XApiLog(
                appId = manager.getAppId(),
                platform = manager.getPlatform().code,
                mode = 3
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                url = url,
                xApiLog = xApiLog,
                grantType = "password",
                clientId = manager.getClientId(),
                account = cellphone,
                hashedPassword = hashedPassword,
                loginMethod = "cellphone"
            ).checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByFacebook(
        accessToken: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = manager.getAppId(),
                    platform = manager.getPlatform().code,
                    mode = 2
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    url = url,
                    xApiLog = xApiLog,
                    grantType = "token-exchange",
                    clientId = manager.getClientId(),
                    providerToken = accessToken,
                    provider = "facebook"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun loginByGoogle(
        accessToken: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = manager.getAppId(),
                    platform = manager.getPlatform().code,
                    mode = 7
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    url = url,
                    xApiLog = xApiLog,
                    grantType = "token-exchange",
                    clientId = manager.getClientId(),
                    providerToken = accessToken,
                    provider = "google"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun loginByFirebaseAnonymousToken(
        anonymousToken: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = manager.getAppId(),
                    platform = manager.getPlatform().code,
                    mode = 5
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    url = url,
                    xApiLog = xApiLog,
                    grantType = "token-exchange",
                    clientId = manager.getClientId(),
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
        code: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        kotlin.runCatching {
            val xApiLog = XApiLog(
                appId = manager.getAppId(),
                platform = manager.getPlatform().code,
                mode = 9
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                url = url,
                xApiLog = xApiLog,
                grantType = "authorization_code",
                clientId = manager.getClientId(),
                redirectUri = redirectUri,
                code = code,
                codeVerifier = codeVerifier
            )
                .checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByCMoneyThirdParty(
        providerToken: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val xApiLog = XApiLog(
                appId = manager.getAppId(),
                platform = manager.getPlatform().code,
                mode = 8
            ).let { gson.toJson(it) }

            service.getIdentityToken(
                url = url,
                xApiLog = xApiLog,
                grantType = "token-exchange",
                clientId = manager.getClientId(),
                providerToken = providerToken,
                provider = "cmoneythirdparty"
            )
                .checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun refreshToken(
        refreshToken: String,
        domain: String,
        url: String
    ): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val xApiLog = XApiLog(
                    appId = manager.getAppId(),
                    platform = manager.getPlatform().code,
                    mode = 6
                ).let { gson.toJson(it) }

                service.getIdentityToken(
                    url = url,
                    xApiLog = xApiLog,
                    grantType = "refresh_token",
                    clientId = manager.getClientId(),
                    refreshToken = refreshToken
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun revokeToken(
        token: String,
        domain: String,
        url: String
    ): Result<RevokeResponseBody> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                service.revokeIdentityToken(
                    url = url,
                    accessToken = manager.getAccessToken().createAuthorizationBearer(),
                    clientId = manager.getClientId(),
                    token = token,
                    tokenType = "refresh_token"
                )
                    .checkResponseBody(gson)
                    .toRealResponse()
            }
        }
}