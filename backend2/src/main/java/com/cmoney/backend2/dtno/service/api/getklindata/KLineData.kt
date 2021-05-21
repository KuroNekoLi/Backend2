package com.cmoney.backend2.dtno.service.api.getklindata

import com.google.gson.annotations.SerializedName

/**
 * K棒資料
 *
 * @property date 日期
 * @property openingPrice 開盤價
 * @property ceilingPrice 最高價
 * @property lowestPrice 最低價
 * @property closePrice 收盤價
 * @property volume 成交量
 * @property priceChange 漲跌
 * @property quoteChange 漲跌幅
 * @property totalVolume 成交金額(千)
 */
data class KLineData(
    @SerializedName("日期")
    val date: String? = null,
    @SerializedName("開盤價")
    val openingPrice: Double? = null,
    @SerializedName("最高價")
    val ceilingPrice: Double? = null,
    @SerializedName("最低價")
    val lowestPrice: Double? = null,
    @SerializedName("收盤價")
    val closePrice: Double? = null,
    @SerializedName("成交量")
    val volume: Int? = null,
    @SerializedName("漲跌")
    val priceChange: Double? = null,
    @SerializedName("漲幅(%)")
    val quoteChange: Double? = null,
    @SerializedName("成交金額(千)")
    val totalVolume: Long? = null
)