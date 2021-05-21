package com.cmoney.backend2.virtualassets.service.api.exchange

import com.google.gson.annotations.SerializedName

data class ExchangeRequestBody (
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)