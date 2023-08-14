package com.cmoney.backend2.ocean.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class OceanSettingAdapterImpl(
    private val setting: BackendSetting
) : OceanSettingAdapter {
    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}