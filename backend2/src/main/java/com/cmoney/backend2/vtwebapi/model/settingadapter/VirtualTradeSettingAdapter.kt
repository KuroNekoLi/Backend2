package com.cmoney.backend2.vtwebapi.model.settingadapter

/**
 * 虛擬下單相關的設定轉接。
 */
interface VirtualTradeSettingAdapter {

    /**
     * 取得網域名稱
     */
    fun getDomain(): String
}