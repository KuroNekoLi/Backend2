package com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks

import com.google.gson.annotations.SerializedName

/**
 * @property closePrice 收盤價
 * @property openPrice 該分鐘的開盤價
 * @property highestPrice 該分鐘的最高價
 * @property lowestPrice 該分鐘的最低價
 * @property time 時間(UnixTime)
 * @property volumn 交易量總計
 */

data class InternationalChartData(
    @SerializedName("ClosePrice")
    val closePrice: Double?,
    @SerializedName("OpenPrice")
    val openPrice: Double?,
    @SerializedName("HighestPrice")
    val highestPrice: Double?,
    @SerializedName("LowestPrice")
    val lowestPrice: Double?,
    @SerializedName("Time")
    val time: Long?,
    @SerializedName("Volumn")
    val volumn: Long?
)