package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.StockData

data class BrokerStockDataResponse(
    val brokerId: String?,
    val brokerShortName: String?,
    val subBrokerId: String?,
    val updateTimeOfUnixTimeSeconds: Long?,
    val inStockData: List<StockData>?
)
