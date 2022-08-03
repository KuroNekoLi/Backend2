package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.google.gson.annotations.SerializedName

class StockInfo internal constructor(
    @SerializedName("TradeType")
    internal val tradeTypeValue: Byte,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("TradeTotalCost")
    val tradeTotalCost: Double,
    @SerializedName("cashDividend")
    val cashDividend: Double,
    @SerializedName("stockDividend")
    val stockDividend: Double,
) {

    constructor(
        tradeType: TradeType,
        amount: Int,
        tradeTotalCost: Double,
        cashDividend: Double,
        stockDividend: Double,
    ) : this(tradeType.value, amount, tradeTotalCost, cashDividend, stockDividend)

    val tradeType get() = TradeType.fromValue(tradeTypeValue)
}
