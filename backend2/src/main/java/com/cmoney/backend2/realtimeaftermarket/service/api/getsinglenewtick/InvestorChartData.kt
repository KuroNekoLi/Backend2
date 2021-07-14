package com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick

import com.google.gson.annotations.SerializedName

/**
 * 大戶散戶買賣超資料畫走勢圖用
 *
 * @property largeBuyQty 該分鐘大戶買張數
 * @property largeQty 該分鐘大戶買賣超張數
 * @property largeSellQty 該分鐘大戶賣張數
 * @property largeTotalQty 今日大戶買賣超累計張數
 * @property smallBuyQty 該分鐘散戶買張數
 * @property smallQty 該分鐘散戶買賣超張數
 * @property smallSellQty 該分鐘散戶賣張數
 * @property smallTotalQty 今日散戶買賣超累計張數
 * @property time 市場時間(UnixTime)
 */
data class InvestorChartData(
    @SerializedName("LargeBuyQty")
    val largeBuyQty: Int?,
    @SerializedName("LargeQty")
    val largeQty: Int?,
    @SerializedName("LargeSellQty")
    val largeSellQty: Int?,
    @SerializedName("LargeTotalQty")
    val largeTotalQty: Int?,
    @SerializedName("SmallBuyQty")
    val smallBuyQty: Int?,
    @SerializedName("SmallQty")
    val smallQty: Int?,
    @SerializedName("SmallSellQty")
    val smallSellQty: Int?,
    @SerializedName("SmallTotalQty")
    val smallTotalQty: Int?,
    @SerializedName("Time")
    val time: Long?
)