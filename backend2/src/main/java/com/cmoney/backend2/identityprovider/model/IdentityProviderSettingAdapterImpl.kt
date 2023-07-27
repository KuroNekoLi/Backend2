package com.cmoney.backend2.identityprovider.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class IdentityProviderSettingAdapterImpl(
    private val setting: BackendSetting
) : IdentityProviderSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}