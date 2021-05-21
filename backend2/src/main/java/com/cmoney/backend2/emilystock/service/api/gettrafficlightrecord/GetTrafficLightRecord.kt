package com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord

import com.google.gson.annotations.SerializedName

data class GetTrafficLightRecord(
    @SerializedName("Response")
    val response: List<TrafficLightRecord?>?
) {
}