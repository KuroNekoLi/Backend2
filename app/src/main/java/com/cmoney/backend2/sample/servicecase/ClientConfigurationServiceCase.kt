package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.clientconfiguration.service.ClientConfigurationWeb
import com.cmoney.backend2.clientconfiguration.service.api.ConfigKey
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class ClientConfigurationServiceCase : ServiceCase {

    private val webImpl by inject<ClientConfigurationWeb>()

    override suspend fun testAll() {
        webImpl.apply {
            getConfig(listOf()).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = " ClientConfiguration"
    }
}