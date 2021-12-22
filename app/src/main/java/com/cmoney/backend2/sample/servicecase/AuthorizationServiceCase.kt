package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.authorization.service.AuthorizationWeb
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class AuthorizationServiceCase : ServiceCase {

    private val webImpl by inject<AuthorizationWeb>()
    private val setting by inject<Setting>(BACKEND2_SETTING)

    override suspend fun testAll() {
        webImpl.apply {
            getExpiredTime(
                type = Type.MOBILE_PAID,
                subjectId = setting.appId.toLong()
            ).logResponse(TAG)
            hasAuth(
                type = Type.MOBILE_PAID,
                subjectId = 2
            ).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "Authorization"
    }
}