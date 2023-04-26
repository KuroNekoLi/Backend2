package com.cmoney.backend2.activity.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class ActivitySettingAdapterImpl(
    private val backendSetting: BackendSetting
) : ActivitySettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}