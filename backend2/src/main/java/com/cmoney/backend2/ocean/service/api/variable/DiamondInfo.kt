package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class DiamondInfo(
    @SerializedName("IntervalOfDiamondLevel")
    val intervalOfDiamondLevel: Int?,
    @SerializedName("Level")
    val level: Int?,
    @SerializedName("Quantity")
    val quantity: Int?
)