package com.cmoney.backend2.ocean.service.api.getsimplechannelinfo

import com.google.gson.annotations.SerializedName

data class ChannelInfo(
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("Image")
    val image: String?
)