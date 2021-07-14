package com.cmoney.backend2.ocean.service.api.checkhasevaluated


import com.google.gson.annotations.SerializedName

data class CheckHasEvaluatedRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ChannelId")
    val channelId: Long
)