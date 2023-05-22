package com.cmoney.backend2.realtimeaftermarket.model

/**
 * 即時盤後設定轉接器
 */
interface RealtimeAfterMarketSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}