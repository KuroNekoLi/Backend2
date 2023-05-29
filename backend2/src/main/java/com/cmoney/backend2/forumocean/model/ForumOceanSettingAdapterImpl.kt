package com.cmoney.backend2.forumocean.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class ForumOceanSettingAdapterImpl(
    private val setting: BackendSetting
) : ForumOceanSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }

    override fun getPathName(): String {
        return ""
    }
}