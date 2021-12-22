package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put

import com.google.gson.annotations.SerializedName

class StockData(
    @SerializedName("StockID")
    val stockId: String,
    @SerializedName("StockInfos")
    val stockInfos: List<StockInfo>
)
