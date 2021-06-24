package com.cmoney.backend2.ocean.service.api.channelwearbadge

import com.google.gson.annotations.SerializedName

data class ChannelWearBadgeRequestBody(
    @SerializedName("ChannelIds")
    val channelIds : List<Long>,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)