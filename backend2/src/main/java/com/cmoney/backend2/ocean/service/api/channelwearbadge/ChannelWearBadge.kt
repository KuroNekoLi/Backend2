package com.cmoney.backend2.ocean.service.api.channelwearbadge
import com.google.gson.annotations.SerializedName

data class ChannelWearBadge(
    @SerializedName("Badges")
    val badges: List<Int?>?,
    @SerializedName("ChannelId")
    val channelId: Long?
)