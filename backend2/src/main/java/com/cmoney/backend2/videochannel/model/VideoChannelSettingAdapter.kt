package com.cmoney.backend2.videochannel.model

/**
 * 影音頻道設定轉接器
 */
interface VideoChannelSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}