package com.cmoney.backend2.ocean.service.api

import com.google.gson.annotations.SerializedName

data class RequestIds (
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("AppId")
    val appId: Int
)