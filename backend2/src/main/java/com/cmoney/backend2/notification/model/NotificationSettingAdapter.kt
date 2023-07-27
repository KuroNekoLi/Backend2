package com.cmoney.backend2.notification.model

/**
 * 推播服務設定轉接器
 */
interface NotificationSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}
