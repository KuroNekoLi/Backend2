package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class AuthorizationWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: AuthorizationService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : AuthorizationWeb {
    override suspend fun getExpiredTime(
        type: Type,
        subjectId: Long,
        domain: String,
        url: String
    ): Result<ExpiredTime> =
        withContext(dispatcher.io()) {
            runCatching {
                service.getExpiredTime(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun hasAuth(
        type: Type,
        subjectId: Long,
        domain: String,
        url: String
    ): Result<Auth> =
        withContext(dispatcher.io()) {
            runCatching {
                service.hasAuth(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
            }
        }

    override suspend fun getPurchasedSubjectIds(
        type: Type,
        domain: String,
        url: String
    ): Result<List<Long>> = withContext(dispatcher.io()){
        runCatching {
            service.getPurchasedSubjectIds(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(gson)
        }
    }
}
