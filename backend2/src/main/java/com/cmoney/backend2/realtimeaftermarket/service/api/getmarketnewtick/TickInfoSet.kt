package com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick

import com.google.gson.annotations.SerializedName

/**
 * @property commonKey 股票編號
 * @property ceilQty 漲停家數
 * @property downQty 下跌家數
 * @property floorQty 跌停家數
 * @property upQty 上漲家數
 * @property openPrice 開盤價
 * @property highPrice 最高價
 * @property lowPrice 最低價
 * @property refPrice 昨收
 * @property salePrice 成交價
 * @property totalVolume 成交總量
 * @property totalTurnover 累計成交金額
 * @property singleTurnover 單次成交金額
 * @property tickTime 揭示時間(UnixTime)
 * @property quoteChange 漲跌幅
 * @property statusCode 狀態編碼(回傳下一次的Polling用)，1:清盤 ; -1 : 暫停交易
 * @property chartData 線圖資料
 * @property totalMarketBuySt 委託買進張數
 * @property totalMarketSellSt 委託賣出張數
 * @property preTotalVolume 昨日成交量
 */
data class TickInfoSet(
    @SerializedName("Commkey")
    val commonKey : String?,
    @SerializedName("CeilQty")
    val ceilQty : Int?,
    @SerializedName("DownQty")
    val downQty : Int?,
    @SerializedName("FloorQty")
    val floorQty : Int?,
    @SerializedName("UpQty")
    val upQty : Int?,
    @SerializedName("OpenPr")
    val openPrice : Double?,
    @SerializedName("HighPr")
    val highPrice : Double?,
    @SerializedName("LowPr")
    val lowPrice : Double?,
    @SerializedName("RefPr")
    val refPrice : Double?,
    @SerializedName("SalePr")
    val salePrice : Double?,
    @SerializedName("TotalVolume")
    val totalVolume : Long?,
    @SerializedName("TotalTurnover")
    val totalTurnover : Long?,
    @SerializedName("SingleTurnover")
    val singleTurnover : Long?,
    @SerializedName("TickTime")
    val tickTime : Long?,
    @SerializedName("QuoteChange")
    val quoteChange : Double?,
    @SerializedName("StatusCode")
    val statusCode : Int?,
    @SerializedName("ChartData")
    val chartData : List<MarketChartData>?,
    @SerializedName("TotalMarketBuySt")
    val totalMarketBuySt : Long?,
    @SerializedName("TotalMarketSellSt")
    val totalMarketSellSt : Long?,
    @SerializedName("PreTotalVolume")
    val preTotalVolume : Long?

)