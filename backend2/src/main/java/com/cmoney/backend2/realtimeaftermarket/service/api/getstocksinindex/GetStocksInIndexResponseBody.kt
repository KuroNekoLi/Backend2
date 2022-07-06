package com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex

import com.google.gson.annotations.SerializedName

/**
 * @property stocks 成份股清單
 */
data class GetStocksInIndexResponseBody(
    @SerializedName("Stocks")
    val stocks: List<Stock>?
)