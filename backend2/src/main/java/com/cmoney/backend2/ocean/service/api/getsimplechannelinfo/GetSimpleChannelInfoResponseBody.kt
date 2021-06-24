package com.cmoney.backend2.ocean.service.api.getsimplechannelinfo
import com.google.gson.annotations.SerializedName


data class GetSimpleChannelInfoResponseBody(
    @SerializedName("ChannelInfos")
    val channelInfo: List<ChannelInfo>?
)