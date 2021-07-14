package com.cmoney.backend2.realtimeaftermarket.service.api.searchustock

import com.google.gson.annotations.SerializedName

/**
 * 美股搜尋結果
 *
 * @property key 股票代號
 * @property marketType 市場類型，固定0
 * @property value 股票名稱
 *
 */
data class UsResultEntry(
    @SerializedName("Key")
    val key: String?,
    @SerializedName("MarketType")
    val marketType: Int?,
    @SerializedName("Value")
    val value: String?
)