package com.cmoney.backend2.cellphone.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CellphoneSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : CellphoneSettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}