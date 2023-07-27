package com.cmoney.backend2.commonuse.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CommonUseSettingAdapterImpl(
    private val setting: BackendSetting
) : CommonUseSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}