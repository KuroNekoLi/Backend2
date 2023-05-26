package com.cmoney.backend2.additioninformationrevisit.model.settingadapter

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class AdditionInformationRevisitSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : AdditionInformationRevisitSettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }

    override fun getPathName(): String {
        return "AdditionInformationRevisit/"
    }
}