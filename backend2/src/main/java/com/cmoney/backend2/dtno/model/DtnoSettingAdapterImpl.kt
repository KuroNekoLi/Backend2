package com.cmoney.backend2.dtno.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class DtnoSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : DtnoSettingAdapter {
    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }
}