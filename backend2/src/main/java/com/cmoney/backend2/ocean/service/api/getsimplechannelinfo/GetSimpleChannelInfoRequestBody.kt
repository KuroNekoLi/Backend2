package com.cmoney.backend2.ocean.service.api.getsimplechannelinfo
import com.google.gson.annotations.SerializedName


data class GetSimpleChannelInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ChannelIds")
    val channelIds: List<Long>,
    @SerializedName("Guid")
    val guid: String
)