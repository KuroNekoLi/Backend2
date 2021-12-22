package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.StockData
import com.google.gson.annotations.SerializedName

class BrokerData(
    @SerializedName("BrokerId")
    val brokerId: String?,
    @SerializedName("SubBrokerId")
    val subBrokerId: String?,
    @SerializedName("InStockData")
    val inStockData: List<StockData>
)
