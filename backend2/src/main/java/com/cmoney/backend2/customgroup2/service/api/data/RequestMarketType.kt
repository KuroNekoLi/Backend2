package com.cmoney.backend2.customgroup2.service.api.data

import com.google.gson.annotations.SerializedName

data class RequestMarketType(
    @SerializedName("marketType")
    val marketType: Int,
    @SerializedName("types")
    val types: List<Int>
)
