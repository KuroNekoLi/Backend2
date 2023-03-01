package com.cmoney.backend2.vtwebapi.model.settingadapter

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class VirtualTradeSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : VirtualTradeSettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}