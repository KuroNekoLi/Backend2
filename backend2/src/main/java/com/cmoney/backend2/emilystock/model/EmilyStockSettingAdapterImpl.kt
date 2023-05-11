package com.cmoney.backend2.emilystock.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class EmilyStockSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : EmilyStockSettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}