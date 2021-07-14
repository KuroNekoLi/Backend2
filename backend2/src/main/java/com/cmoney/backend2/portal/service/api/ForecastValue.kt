package com.cmoney.backend2.portal.service.api

import com.google.gson.annotations.SerializedName

/**
 * 預測多空值
 */
enum class ForecastValue(val value : Int) {
    @SerializedName("0")
    Bear(0),
    @SerializedName("1")
    Bull(1)
}