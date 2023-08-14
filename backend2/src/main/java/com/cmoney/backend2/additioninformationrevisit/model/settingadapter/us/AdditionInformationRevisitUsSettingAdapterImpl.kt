package com.cmoney.backend2.additioninformationrevisit.model.settingadapter.us

class AdditionInformationRevisitUsSettingAdapterImpl : AdditionInformationRevisitUsSettingAdapter {
    override fun getDomain(): String {
        return "https://internal.cmoney.tw/"
    }

    override fun getPathName(): String {
        return "AdditionInformationRevisit/"
    }
}