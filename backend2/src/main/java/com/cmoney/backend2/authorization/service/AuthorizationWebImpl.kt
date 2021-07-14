package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.ExpiredTime
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
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : AuthorizationWeb {
    override suspend fun getExpiredTime(type: String, subjectId: Int): Result<ExpiredTime> {
        return withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getExpiredTime(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    type = type,
                    subjectId = subjectId
                ).checkResponseBody(gson)
            }
        }
    }
}