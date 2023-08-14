package com.cmoney.backend2.media.model

/**
 * MobileService-Media服務設定轉接器
 */
interface MediaSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}