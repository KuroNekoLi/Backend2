package com.cmoney.backend2.trial.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class TrialSettingAdapterImpl(
    private val setting: BackendSetting
) : TrialSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}