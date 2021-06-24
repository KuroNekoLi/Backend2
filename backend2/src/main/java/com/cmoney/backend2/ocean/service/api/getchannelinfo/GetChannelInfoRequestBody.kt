package com.cmoney.backend2.ocean.service.api.getchannelinfo
import com.google.gson.annotations.SerializedName


data class GetChannelInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ChannelId")
    val channelId: Long,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("NeedInfo")
    val needInfo: Int
)