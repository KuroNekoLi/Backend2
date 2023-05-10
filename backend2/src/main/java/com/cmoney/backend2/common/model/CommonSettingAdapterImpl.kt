package com.cmoney.backend2.common.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CommonSettingAdapterImpl(
    private val setting: BackendSetting
) : CommonSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}