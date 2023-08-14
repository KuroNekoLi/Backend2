package com.cmoney.backend2.billing.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class BillingSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : BillingSettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}