package com.cmoney.backend2.ocean.service.api.spinoffblacklist

import com.google.gson.annotations.SerializedName

data class SpinOffBlackListRequestBody (
    @SerializedName("BlackChannelId")
    val blackChannelId : Long,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)