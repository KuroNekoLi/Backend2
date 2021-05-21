package com.cmoney.backend2.notification2.service

import com.google.gson.annotations.SerializedName

data class TestParameter7(
    @SerializedName("DealPrice", alternate = ["dealPrice"])
    val dealPrice: Double?,
    @SerializedName("WaveRange", alternate = ["waveRange"])
    val waveRange: Double?,
    @SerializedName("DayOrNight", alternate = ["dayOrNight"])
    val dayOrNight: String?
)
