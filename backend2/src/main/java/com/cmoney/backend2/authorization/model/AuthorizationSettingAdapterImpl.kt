package com.cmoney.backend2.authorization.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class AuthorizationSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : AuthorizationSettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}