package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigInfoParser
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigType
import com.cmoney.backend2.clientconfiguration.service.api.ConfigKey
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class ClientConfigurationWebImpl(
    private val service: ClientConfigurationService,
    private val jsonParser: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ClientConfigurationWeb {

    override suspend fun getConfig(list: List<ConfigKey>): Result<List<ClientConfigType>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val keyList = list.map { it.stringValue }
                val response = service.getConfig(keyList)
                val responseBody = response.checkResponseBody(jsonParser)
                responseBody.configs.mapNotNull {
                    ClientConfigInfoParser.getClientConfigType(it, jsonParser)
                }
            }
        }

}