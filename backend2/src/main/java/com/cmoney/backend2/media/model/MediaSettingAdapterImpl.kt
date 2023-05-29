package com.cmoney.backend2.media.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class MediaSettingAdapterImpl(
    private val setting: BackendSetting
) : MediaSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}