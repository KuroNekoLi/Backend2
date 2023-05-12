package com.cmoney.backend2.note_extension.model

import com.cmoney.backend2.base.model.setting.backend.BackendSetting

class NoteExtensionSettingAdapterImpl(
    private val setting: BackendSetting
) : NoteExtensionSettingAdapter {

    override fun getDomain(): String {
        return setting.getDomainUrl()
    }
}