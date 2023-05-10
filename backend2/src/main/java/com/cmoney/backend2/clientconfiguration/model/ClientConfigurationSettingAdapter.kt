package com.cmoney.backend2.clientconfiguration.model

/**
 * 用戶端設定服務設定轉接器
 */
interface ClientConfigurationSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}