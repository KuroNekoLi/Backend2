package com.cmoney.backend2.tickdata.model

/**
 * TickData服務設定轉接器
 */
interface TickDataSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}