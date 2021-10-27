package com.cmoney.backend2.sample.servicecase

import android.util.Log
import com.cmoney.backend2.clientconfiguration.service.ClientConfigurationWeb
import com.cmoney.backend2.clientconfiguration.service.api.ConfigKey
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class ClientConfigurationServiceCase : ServiceCase {

    private val webImpl by inject<ClientConfigurationWeb>()

    override suspend fun testAll() {
        webImpl.apply {
          val a=  getConfig(listOf(ConfigKey.KOL)).getOrNull()
            Log.d("aaaa",a.toString())
        }
    }

    companion object {
        private const val TAG = " ClientConfiguration"
    }
}