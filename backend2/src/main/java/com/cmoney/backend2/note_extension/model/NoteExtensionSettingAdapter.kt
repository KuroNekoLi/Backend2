package com.cmoney.backend2.note_extension.model

/**
 * NoteExtension服務設定轉接器
 */
interface NoteExtensionSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}