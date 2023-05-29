package com.cmoney.backend2.chipk.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class ChipKSettingAdapterImpl(
    private val setting: BackendSetting
) : ChipKSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}