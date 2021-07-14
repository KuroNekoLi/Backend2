package com.cmoney.backend2.additioninformationrevisit.service.testing

import com.google.gson.annotations.SerializedName

data class CandleChartRequest(
    @SerializedName("Commodity")
    val commodity: String,
    @SerializedName("MinuteInterval")
    val minuteInterval: Int
)