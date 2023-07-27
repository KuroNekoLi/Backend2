package com.cmoney.backend2.notes.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class NotesSettingAdapterImpl(
    private val setting: BackendSetting
) : NotesSettingAdapter {
    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}