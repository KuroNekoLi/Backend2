package com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex

import com.google.gson.annotations.SerializedName

data class GetStocksInIndexResponseBody(
    @SerializedName("Stocks")
    val stocks: List<Stock>?
)