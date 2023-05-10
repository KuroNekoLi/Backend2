package com.cmoney.backend2.common.model

/**
 * MobileService通用設定轉接器
 */
interface CommonSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}