package com.cmoney.backend2.productdataprovider.model

/**
 * 產品資料提供者服務設定轉接器
 */
interface ProductDataProviderSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}