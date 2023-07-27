package com.cmoney.backend2.profile.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class ProfileSettingAdapterImpl(
    private val setting: BackendSetting
) : ProfileSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}