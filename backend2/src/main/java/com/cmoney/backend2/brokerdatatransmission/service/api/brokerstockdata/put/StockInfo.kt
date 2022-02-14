package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.google.gson.annotations.SerializedName

class StockInfo internal constructor(
    @SerializedName("TradeType")
    internal val tradeTypeValue: Byte,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("TradeTotalCost")
    val tradeTotalCost: Double
) {

    constructor(
        tradeType: TradeType,
        amount: Int,
        tradeTotalCost: Double
    ) : this(tradeType.value, amount, tradeTotalCost)

    val tradeType get() = TradeType.fromValue(tradeTypeValue)

}
