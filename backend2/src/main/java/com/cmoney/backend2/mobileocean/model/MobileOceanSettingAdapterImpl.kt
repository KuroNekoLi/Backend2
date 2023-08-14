package com.cmoney.backend2.mobileocean.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class MobileOceanSettingAdapterImpl(
    private val setting: BackendSetting
) : MobileOceanSettingAdapter {
    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}