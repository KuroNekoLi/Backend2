package com.cmoney.backend2.notification2.model

/**
 * Notification2服務設定轉接器
 */
interface Notification2SettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}