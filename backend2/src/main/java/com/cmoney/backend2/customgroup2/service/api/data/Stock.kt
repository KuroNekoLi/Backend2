package com.cmoney.backend2.customgroup2.service.api.data


/**
 * 股市標的
 *
 * @property id 編號
 * @property name 顯示名稱
 * @property marketType 市場類別
 */
data class Stock(
    val id: String?,
    val name: String?,
    val marketType: MarketType?
)