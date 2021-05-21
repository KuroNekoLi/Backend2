package com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail

import com.google.gson.annotations.SerializedName

data class DetailInfo(
    @SerializedName("TickTime")
    val tickTime: Long?,
    @SerializedName("TradePr")
    val tradePrice: Double?,
    @SerializedName("BuyPr")
    val buyPrice: Double?,
    @SerializedName("SellPr")
    val sellPrice: Double?,
    @SerializedName("TickQty")
    val tickQuantity: Int?,
    @SerializedName("TotalQty")
    val totalQuantity: Long?,
    @SerializedName("BuyOrSell")
    val buyOrSell: Byte?,
    @SerializedName("OrderPriceFlag")
    val orderPriceFlag: Byte?
) {
}