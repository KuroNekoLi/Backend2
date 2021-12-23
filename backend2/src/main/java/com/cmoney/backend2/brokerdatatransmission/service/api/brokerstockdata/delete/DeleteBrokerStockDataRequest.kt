package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.delete

import com.google.gson.annotations.SerializedName

class DeleteBrokerStockDataRequest(
    @SerializedName("CountryISOCode")
    val countryISOCode: Int,
    @SerializedName("BrokerIds")
    val brokerIds: List<String>
)
