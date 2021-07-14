package com.cmoney.backend2.sample.servicecase.data

import com.google.gson.annotations.SerializedName

data class Notification2TestParameter(
    @SerializedName("DealPrice", alternate = ["dealPrice"])
    val dealPrice: Double?,
    @SerializedName("WaveRange", alternate = ["waveRange"])
    val waveRange: Double?,
    @SerializedName("DayOrNight", alternate = ["dayOrNight"])
    val dayOrNight: String?
)
