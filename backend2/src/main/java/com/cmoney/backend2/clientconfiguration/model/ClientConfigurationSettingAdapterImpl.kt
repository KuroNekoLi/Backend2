package com.cmoney.backend2.clientconfiguration.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class ClientConfigurationSettingAdapterImpl(
    private val setting: BackendSetting
) : ClientConfigurationSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}