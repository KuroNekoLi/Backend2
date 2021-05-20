package com.cmoney.backend2.portal.service.api.getpersonactivityhistory

import com.cmoney.backend2.portal.service.api.ForecastValue
import com.google.gson.annotations.SerializedName

data class GetPersonActivityHistory(
    @SerializedName("ForecastValue")
    val forecastValue: ForecastValue?,
    @SerializedName("HasWin")
    val hasWin: Boolean?,
    @SerializedName("Prize")
    val prize: Int?,
    @SerializedName("TradeDate")
    val tradeDate: Long?
)