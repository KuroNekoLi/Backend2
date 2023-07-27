package com.cmoney.backend2.userbehavior.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class UserBehaviorSettingAdapterImpl(
    private val setting: BackendSetting
) : UserBehaviorSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}