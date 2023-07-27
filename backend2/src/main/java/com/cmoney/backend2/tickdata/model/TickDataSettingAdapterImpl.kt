package com.cmoney.backend2.tickdata.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class TickDataSettingAdapterImpl(
    private val setting: BackendSetting
) : TickDataSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}