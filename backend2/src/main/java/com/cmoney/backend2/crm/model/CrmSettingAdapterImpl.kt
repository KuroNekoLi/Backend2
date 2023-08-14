package com.cmoney.backend2.crm.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CrmSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : CrmSettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}