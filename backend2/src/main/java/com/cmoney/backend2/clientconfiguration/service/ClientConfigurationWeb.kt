package com.cmoney.backend2.clientconfiguration.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.clientconfiguration.service.api.ClientConfigType
import com.cmoney.backend2.clientconfiguration.service.api.ConfigKey

interface ClientConfigurationWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得設定檔
     *
     * @param list config參數集合
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getConfig(
        list: List<ConfigKey>,
        domain: String = manager.getClientConfigurationSettingAdapter().getDomain(),
        url: String = "${domain}ClientConfiguration/api/config/get"
    ): Result<List<ClientConfigType>>
}