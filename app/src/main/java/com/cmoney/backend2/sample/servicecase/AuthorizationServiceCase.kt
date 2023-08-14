package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.authorization.service.AuthorizationWeb
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class AuthorizationServiceCase : ServiceCase {

    private val web by inject<AuthorizationWeb>()
    private val manager by inject<GlobalBackend2Manager>()
    override suspend fun testAll() {
        web.run {
            getExpiredTime(
                type = Type.MOBILE_PAID,
                subjectId = manager.getAppId().toLong()
            ).logResponse(TAG)
            hasAuth(
                type = Type.MOBILE_PAID,
                subjectId = manager.getAppId().toLong()
            ).logResponse(TAG)
            getPurchasedSubjectIds(
                type = Type.MOBILE_PAID
            ).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "Authorization"
    }
}