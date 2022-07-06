package com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex

import com.google.gson.annotations.SerializedName

data class GetStockSinIndexResponseBody(
    @SerializedName("Stocks")
    val stocks: List<Stock>?
)