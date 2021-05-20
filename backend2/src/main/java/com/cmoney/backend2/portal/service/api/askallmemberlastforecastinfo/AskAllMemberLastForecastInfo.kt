package com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.google.gson.annotations.SerializedName


data class AskAllMemberLastForecastInfo(
    @SerializedName("Result")
    val result: List<StockLastForecastInfo>?
)

data class StockLastForecastInfo(
    @SerializedName("Commkey")
    val commkey: String?,
    @SerializedName("ForecastResult")
    val forecastResult: ForecastValue?,
    @SerializedName("HasWin")
    val hasWin: Boolean?,
    @SerializedName("Prize")
    val prize: Int?,
    @SerializedName("TradeDate")
    val tradeDate: Long?
)