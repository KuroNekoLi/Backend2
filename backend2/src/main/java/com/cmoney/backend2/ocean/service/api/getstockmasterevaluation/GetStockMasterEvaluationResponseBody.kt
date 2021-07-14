package com.cmoney.backend2.ocean.service.api.getstockmasterevaluation

import com.google.gson.annotations.SerializedName

data class GetStockMasterEvaluationResponseBody (
    @SerializedName("AvgScores")
    val avgScores: Double?,
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ClosePr")
    val closePr: Double?,
    @SerializedName("Trends")
    val trends: List<Trend?>?
)