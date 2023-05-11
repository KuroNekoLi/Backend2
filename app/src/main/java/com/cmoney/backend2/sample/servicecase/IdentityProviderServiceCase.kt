package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.identityprovider.service.IdentityProviderWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class IdentityProviderServiceCase: ServiceCase {

    private val identityProviderWeb by inject<IdentityProviderWeb>()

    override suspend fun testAll() {
        identityProviderWeb.isTokenLatest()
            .logResponse(TAG)
        identityProviderWeb.refreshToken()
            .logResponse(TAG)
        identityProviderWeb.revokeToken()
            .logResponse(TAG)
        identityProviderWeb.refreshToken()
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "IdentityProvider"
    }
}