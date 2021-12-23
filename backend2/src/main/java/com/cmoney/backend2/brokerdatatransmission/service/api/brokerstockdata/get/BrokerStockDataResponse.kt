package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.google.gson.annotations.SerializedName

data class BrokerStockDataResponse(
    @SerializedName("brokerId")
    val brokerId: String?,
    @SerializedName("brokerShortName")
    val brokerShortName: String?,
    @SerializedName("subBrokerId")
    val subBrokerId: String?,
    @SerializedName("updateTimeOfUnixTimeSeconds")
    val updateTimeOfUnixTimeSeconds: Long?,
    @SerializedName("inStockData")
    val inStockData: List<StockData>?
)
