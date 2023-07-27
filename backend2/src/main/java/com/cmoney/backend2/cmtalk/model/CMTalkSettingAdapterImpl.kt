package com.cmoney.backend2.cmtalk.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class CMTalkSettingAdapterImpl(
    private val setting: BackendSetting
) : CMTalkSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}