package com.cmoney.backend2.additioninformationrevisit.model.settingadapter.tw

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class AdditionInformationRevisitTwSettingAdapterImpl(
    private val backendSetting: BackendSetting
) : AdditionInformationRevisitTwSettingAdapter {

    override fun getDomain(): String {
        return backendSetting.getDomainUrl()
    }

    override fun getPathName(): String {
        return "AdditionInformationRevisit/"
    }
}