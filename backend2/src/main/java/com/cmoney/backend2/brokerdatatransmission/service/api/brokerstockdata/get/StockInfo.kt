package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.google.gson.annotations.SerializedName

class StockInfo(
    @SerializedName("tradeType")
    val tradeType: TradeType?,
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("tradeTotalCost")
    val tradeTotalCost: Double?
)