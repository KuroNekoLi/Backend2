package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.google.gson.annotations.SerializedName

class StockInfo(
    @SerializedName("TradeType")
    val tradeType: TradeType,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("TradeTotalCost")
    val tradeTotalCost: Double
)
