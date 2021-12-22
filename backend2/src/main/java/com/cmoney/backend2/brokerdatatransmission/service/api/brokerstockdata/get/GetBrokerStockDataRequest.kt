package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.get

import com.google.gson.annotations.SerializedName

data class GetBrokerStockDataRequest(
    @SerializedName("CountryISOCode")
    val code: Int,
    @SerializedName("BrokerId")
    val brokerId: String?,
    @SerializedName("SubBrokerId")
    val subBrokerId: String?
)
