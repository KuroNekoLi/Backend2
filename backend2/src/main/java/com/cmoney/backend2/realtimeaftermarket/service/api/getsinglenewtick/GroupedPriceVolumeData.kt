package com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick


import com.google.gson.annotations.SerializedName

/**
 * 分價量及內外盤比資料(以價格做分組)
 *
 * @property askQty 該價格於外盤的成交總量
 * @property bidQty 該價格於內盤的成交總量
 * @property price 價格
 * @property totalVolume 該價格的總成交量
 */
data class GroupedPriceVolumeData(
    @SerializedName("AskQty")
    val askQty: Int?,
    @SerializedName("BidQty")
    val bidQty: Int?,
    @SerializedName("Price")
    val price: Double?,
    @SerializedName("TotalVolume")
    val totalVolume: Double?
)