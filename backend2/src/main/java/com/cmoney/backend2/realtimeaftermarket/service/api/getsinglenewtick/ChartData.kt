package com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick


import com.google.gson.annotations.SerializedName

/**
 * 畫走勢圖用的資料
 *
 * @property averagePrice 即時均價
 * @property closePrice 該分鐘的最後交易價
 * @property highestPrice 該分鐘的最高價
 * @property lowestPrice 該分鐘的最低價
 * @property openPrice 該分鐘的開盤價
 * @property time 市場時間(UnixTime)
 * @property volumn 該分鐘的交易量
 */
data class ChartData(
    @SerializedName("AveragePrice")
    val averagePrice: Double?,
    @SerializedName("ClosePrice")
    val closePrice: Double?,
    @SerializedName("HighestPrice")
    val highestPrice: Double?,
    @SerializedName("LowestPrice")
    val lowestPrice: Double?,
    @SerializedName("OpenPrice")
    val openPrice: Double?,
    @SerializedName("Time")
    val time: Long?,
    @SerializedName("Volumn")
    val volumn: Int?
)