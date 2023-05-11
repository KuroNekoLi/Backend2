package com.cmoney.backend2.frontendlogger.model


/**
 * FrontEndLogger服務設定轉接器
 */
interface FrontEndLoggerSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}