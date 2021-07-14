package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class AuthorInfo(
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("ChannelType")
    val channelType: OceanChannelType?,
    @SerializedName("DiamondInfo")
    val diamondInfo: DiamondInfo?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?
)