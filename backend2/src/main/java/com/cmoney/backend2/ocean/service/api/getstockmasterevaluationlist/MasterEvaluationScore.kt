package com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist


import com.google.gson.annotations.SerializedName

data class MasterEvaluationScore(
    @SerializedName("AvgScores")
    val avgScores: Double?,
    @SerializedName("ChannelID")
    val channelID: Long?,
    @SerializedName("StockId")
    val stockId: String?
)