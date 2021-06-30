package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.logRequest
import com.cmoney.backend2.base.extension.runCatchingWithLog
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBody
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class IdentityProviderWebImpl(
    private val service: IdentityProviderService,
    private val gson: Gson,
    private val setting: Setting,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : IdentityProviderWeb {

    override suspend fun isTokenLatest(): Result<Boolean> = withContext(dispatcherProvider.io()) {
        runCatchingWithLog {
            val response = logRequest(setting) {
                service.isTokenLatest(accessToken = setting.accessToken.createAuthorizationBearer())
            }
            response.checkResponseBody(gson)
                .toRealResponse()
                .isSuccess
        }
    }

    override suspend fun loginByEmail(
        account: String,
        hashedPassword: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatchingWithLog {
            this.userId = setting.identityToken.getClientId()
            val response = logRequest(setting) {
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
                )
            }
            response.checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByCellphone(
        cellphone: String,
        hashedPassword: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatchingWithLog {
            val response = logRequest(setting) {
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
                )
            }
            response.checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun loginByFacebook(accessToken: String): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            runCatchingWithLog {
                val response = logRequest(setting) {
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
                }
                response.checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun loginByFirebaseAnonymousToken(anonymousToken: String): Result<GetTokenResponseBody> =
        withContext(dispatcherProvider.io()) {
            runCatchingWithLog {
                val response = logRequest(setting) {
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
                }
                response.checkResponseBody(gson)
                    .toRealResponse()
            }
        }

    override suspend fun refreshToken(
        refreshToken: String
    ): Result<GetTokenResponseBody> = withContext(dispatcherProvider.io()) {
        runCatchingWithLog {
            val response = logRequest(setting) {
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
            }
            response.checkResponseBody(gson)
                .toRealResponse()
        }
    }

    override suspend fun revokeToken(
        token: String
    ): Result<RevokeResponseBody> = withContext(dispatcherProvider.io()) {
        runCatchingWithLog {
            val response = logRequest(setting) {
                service.revokeIdentityToken(
                    accessToken = setting.accessToken.createAuthorizationBearer(),
                    clientId = setting.clientId,
                    token = token,
                    tokenType = "refresh_token"
                )
            }
            response.checkResponseBody(gson)
                .toRealResponse()
        }
    }
}