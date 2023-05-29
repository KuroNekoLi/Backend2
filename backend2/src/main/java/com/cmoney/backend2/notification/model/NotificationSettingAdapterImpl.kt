package com.cmoney.backend2.notification.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class NotificationSettingAdapterImpl(
    private val setting: BackendSetting
) : NotificationSettingAdapter {
    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}