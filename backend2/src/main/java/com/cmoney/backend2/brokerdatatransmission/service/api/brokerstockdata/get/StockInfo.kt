package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.google.gson.annotations.SerializedName

data class StockInfo(
    @SerializedName("tradeType")
    internal val tradeTypeValue: Byte?,
    @SerializedName("amount")
    val amount: Long?,
    @SerializedName("tradeTotalCost")
    val tradeTotalCost: Double?,
    @SerializedName("cashDividend")
    val cashDividend: Double?,
    @SerializedName("stockDividend")
    val stockDividend: Double?,
) {
    val tradeType get() = tradeTypeValue?.let { TradeType.fromValue(tradeTypeValue) }
}
