package com.cmoney.backend2.virtualtrading2.model.requestconfig

/**
 * 所有Request的設定
 */
interface VirtualTradingRequestConfig {
    /**
     * 取得網域名稱
     */
    fun getDomain(): String

    /**
     * 取得BearerToken
     */
    fun getBearerToken(): String
}