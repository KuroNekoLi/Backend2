package com.cmoney.backend2.centralizedimage.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CentralizedImageSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : CentralizedImageSettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}