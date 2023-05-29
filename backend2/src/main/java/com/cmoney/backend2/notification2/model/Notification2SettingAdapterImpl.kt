package com.cmoney.backend2.notification2.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class Notification2SettingAdapterImpl(
    private val setting: BackendSetting
) : Notification2SettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}