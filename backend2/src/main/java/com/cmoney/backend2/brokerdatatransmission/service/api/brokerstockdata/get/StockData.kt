package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.google.gson.annotations.SerializedName

data class StockData(
    @SerializedName("stockID")
    val stockId: String?,
    @SerializedName("stockInfos")
    val stockInfos: List<StockInfo>?
)
