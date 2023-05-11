package com.cmoney.backend2.frontendlogger.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class FrontEndLoggerSettingAdapterImpl(
    private val setting: BackendSetting
) : FrontEndLoggerSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}