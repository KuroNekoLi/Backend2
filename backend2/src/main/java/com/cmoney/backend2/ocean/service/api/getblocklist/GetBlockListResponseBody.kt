package com.cmoney.backend2.ocean.service.api.getblocklist

import com.google.gson.annotations.SerializedName

data class GetBlockListResponseBody(
    @SerializedName("ChannelIds")
    val channelIds: List<Long?>?
)