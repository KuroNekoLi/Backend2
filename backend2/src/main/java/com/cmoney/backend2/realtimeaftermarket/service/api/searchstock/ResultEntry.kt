package com.cmoney.backend2.realtimeaftermarket.service.api.searchstock

import com.google.gson.annotations.SerializedName

/**
 * 台股搜尋結果
 *
 * @property key 股票代號
 * @property marketType 市場類型，(0:未知, 2:上市, 3:上櫃)
 * @property value 股票名稱
 *
 */
data class ResultEntry(
    @SerializedName("Key")
    val key: String?,
    @SerializedName("Value")
    val value: String?,
    @SerializedName("MarketType")
    val marketType: Int?
)