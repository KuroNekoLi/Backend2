package com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage

import com.google.gson.annotations.SerializedName

data class MultipleMovingAverageData(
    @SerializedName("dataPoint")
    val dataPoint: Int?,
    @SerializedName("movingAverage")
    val movingAverage: List<MovingAverageItem?>?
) {
    data class MovingAverageItem(
        @SerializedName("KT")
        val time: Long?,
        @SerializedName("Price")
        val price: Double?
    )
}