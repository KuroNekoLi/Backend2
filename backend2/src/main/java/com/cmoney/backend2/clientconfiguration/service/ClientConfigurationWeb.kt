package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigType
import com.cmoney.backend2.clientconfiguration.service.api.ConfigKey

interface ClientConfigurationWeb {
    /**
     * 取得設定檔
     */
    suspend fun getConfig(list: List<ConfigKey>): Result<List<ClientConfigType>>


}