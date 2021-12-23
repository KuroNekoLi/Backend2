package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.google.gson.annotations.SerializedName

class GetBrokerStockDataRequest(
    @SerializedName("CountryISOCode")
    val code: Int
)
