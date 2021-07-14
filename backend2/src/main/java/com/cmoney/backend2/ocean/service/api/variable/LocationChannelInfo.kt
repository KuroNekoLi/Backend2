package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class LocationChannelInfo(
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("ChannelType")
    val channelType: OceanChannelType?
)