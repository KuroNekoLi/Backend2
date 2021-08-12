package com.cmoney.backend2.chipk.service.api.internationalkchart

/**
 * 商品類別
 *
 * @property value API時的數值
 */
enum class ProductType(val value: Int) {

    /**
     * 國際指數
     */
    InternationalIndex(1),

    /**
     * 全球期貨
     */
    InternationalFutures(2)
}