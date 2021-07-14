package com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist


import com.google.gson.annotations.SerializedName

data class GetCommListResponseBody(
    @SerializedName("Products")
    val products: List<Product>?,
    @SerializedName("ResponseCode")
    val responseCode: Int?
)