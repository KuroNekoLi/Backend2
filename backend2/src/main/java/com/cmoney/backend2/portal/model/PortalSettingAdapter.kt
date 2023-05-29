package com.cmoney.backend2.portal.model

/**
 * Portal服務設定轉接器
 */
interface PortalSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}