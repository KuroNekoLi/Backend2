package com.cmoney.backend2.realtimeaftermarket.service.api.getisintradeday

import com.google.gson.annotations.SerializedName

data class GetIsInTradeDayResponseBody(
    /**
     * 是否盤中
     */
    @SerializedName("IsIntraday")
    val isInTradeDay : Boolean?
)