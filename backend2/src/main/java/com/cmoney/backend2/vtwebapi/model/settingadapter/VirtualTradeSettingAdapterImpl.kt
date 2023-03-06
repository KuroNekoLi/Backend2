package com.cmoney.backend2.vtwebapi.model.settingadapter

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

/**
 * 虛擬下單V1設定轉接器
 */
class VirtualTradeSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : VirtualTradeSettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}