package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class AuthorizationWebImpl(
    private val gson: Gson,
    private val service: AuthorizationService,
    override val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : AuthorizationWeb {
    override suspend fun getExpiredTime(type: Type, subjectId: Long): Result<ExpiredTime> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getExpiredTime(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    type = type.value,
                    subjectId = subjectId
                ).checkResponseBody(gson)
            }
        }

    override suspend fun hasAuth(host: String, type: Type, subjectId: Long): Result<Auth> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val requestUrl = "${host}AuthorizationServer/Authorization/${type.value}/$subjectId"
                service.hasAuth(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    url = requestUrl
                ).checkResponseBody(gson)
            }
        }
}