package com.cmoney.backend2.customgroup2.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CustomGroup2SettingAdapterImpl(
    private val backendSetting: BackendSetting
) : CustomGroup2SettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}