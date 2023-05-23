package com.cmoney.backend2.virtualassets.model

/**
 * 虛擬資產設定轉接器
 */
interface VirtualAssetsSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}