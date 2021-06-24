package com.cmoney.backend2.ocean.service.api.joinclub


import com.google.gson.annotations.SerializedName

data class JoinClubRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ChannelId")
    val channelId: Long,
    @SerializedName("Message")
    val message: String
)