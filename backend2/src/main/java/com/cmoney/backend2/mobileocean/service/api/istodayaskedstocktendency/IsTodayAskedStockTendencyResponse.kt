package com.cmoney.backend2.mobileocean.service.api.istodayaskedstocktendency

import com.google.gson.annotations.SerializedName

/**
 * 今天是否跪求過
 *
 * @property isAsked
 */
data class IsTodayAskedStockTendencyResponse(
    @SerializedName("IsAsked")
    val isAsked: Boolean?
)