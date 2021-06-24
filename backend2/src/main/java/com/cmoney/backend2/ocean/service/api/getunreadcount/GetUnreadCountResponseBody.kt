package com.cmoney.backend2.ocean.service.api.getunreadcount
import com.google.gson.annotations.SerializedName


data class GetUnreadCountResponseBody(
    @SerializedName("Data")
    val data: Int?
)