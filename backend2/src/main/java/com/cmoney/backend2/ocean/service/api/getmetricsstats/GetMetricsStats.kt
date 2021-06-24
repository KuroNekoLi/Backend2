package com.cmoney.backend2.ocean.service.api.getmetricsstats

import com.google.gson.annotations.SerializedName

data class GetMetricsStats(
    @SerializedName("ChannelId")
    val channelId: Int?,
    @SerializedName("Count")
    val count: Int?,
    @SerializedName("MTime")
    val mTime: String?,
    @SerializedName("MetricsId")
    val metricsId: Int?
)
