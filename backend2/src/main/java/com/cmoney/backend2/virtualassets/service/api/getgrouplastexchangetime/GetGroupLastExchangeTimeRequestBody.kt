package com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime


import com.google.gson.annotations.SerializedName

data class GetGroupLastExchangeTimeRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ExchangeIds")
    val exchangeIds: List<Long>
)