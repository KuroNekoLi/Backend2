package com.cmoney.backend2.customgroup.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CustomGroupSettingAdapterImpl(
    private val setting: BackendSetting
) : CustomGroupSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}