package com.cmoney.backend2.ocean.service.api.putonblacklist

import com.google.gson.annotations.SerializedName

data class PutOnBlackListRequestBody(
    @SerializedName("BlackChannelId")
    val blackChannelId : Long,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)