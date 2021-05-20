package com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.google.gson.annotations.SerializedName


data class AskMemberLastForecastInfo(
    @SerializedName("Result")
    val result: LastForecastInfo?
)

data class LastForecastInfo (
    @SerializedName("ForecastResult")
    val forecastResult: ForecastValue?,
    @SerializedName("HasWin")
    val hasWin: Boolean?,
    @SerializedName("Prize")
    val prize: Int?,
    @SerializedName("TradeDate")
    val tradeDate: Long?
)