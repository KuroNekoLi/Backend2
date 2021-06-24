package com.cmoney.backend2.ocean.service.api.getblacklist

import com.google.gson.annotations.SerializedName

data class GetBlackListResponseBody(
    @SerializedName("ChannelIds")
    val channelIds: List<Long?>?
)