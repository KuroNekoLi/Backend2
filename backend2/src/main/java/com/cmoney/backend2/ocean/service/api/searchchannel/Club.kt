package com.cmoney.backend2.ocean.service.api.searchchannel

import com.google.gson.annotations.SerializedName

data class Club(
    @SerializedName("ChannelId")
    val channelId: Int?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Image")
    val image: String?
)