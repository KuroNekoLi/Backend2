package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put

import com.google.gson.annotations.SerializedName

class PutBrokerStockDataRequest(
    @SerializedName("CountryISOCode")
    val code: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("BrokerData")
    val brokerData: BrokerData
)
