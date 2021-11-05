package com.cmoney.backend2.customgroup2.service.api.data

/**
 * 自選股群組
 *
 * @property id 代號
 * @property name 顯示名稱
 * @property marketType 市場類別
 * @property stocks 股票清單
 */
data class CustomGroup(
    val id: String,
    val name: String?,
    val marketType: DocMarketType?,
    val stocks: List<String>?
)
