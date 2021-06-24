package com.cmoney.backend2.ocean.service.api.getmetricsstats

import com.google.gson.annotations.SerializedName

data class GetMetricsStatsRequestBody(
    @SerializedName("Guid")
    val guid : String,

    @SerializedName("AppId")
    val appId: Int
)