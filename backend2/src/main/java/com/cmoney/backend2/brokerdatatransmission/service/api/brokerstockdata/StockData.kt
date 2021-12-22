package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get.StockType
import com.google.gson.annotations.SerializedName

data class StockData(
    @SerializedName("TradeType")
    val tradeType: StockType?,
    @SerializedName("Amount")
    val amount: Int?,
    @SerializedName("TradeTotalCost")
    val tradeTotalCost: Double?
)
