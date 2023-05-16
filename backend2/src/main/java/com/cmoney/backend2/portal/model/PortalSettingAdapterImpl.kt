package com.cmoney.backend2.portal.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class PortalSettingAdapterImpl(
    private val setting: BackendSetting
) : PortalSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}