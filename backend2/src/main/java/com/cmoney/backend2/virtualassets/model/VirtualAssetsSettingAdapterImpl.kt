package com.cmoney.backend2.virtualassets.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class VirtualAssetsSettingAdapterImpl(
    private val setting: BackendSetting
) : VirtualAssetsSettingAdapter {
    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}