package com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo

import com.google.gson.annotations.SerializedName

data class TickInfoSet(

    /**
     * 股票編號
     */
    @SerializedName("Commkey")
    val commKey: String?,

    /**
     * 內外盤(內盤:0 外盤:1)
     */
    @SerializedName("BuyOrSell")
    val buyOrSell: Int?,

    /**
     * 參考價
     */
    @SerializedName("RefPrice")
    val refPrice: Double?,

    /**
     * 現在的價位
     */
    @SerializedName("DealPrice")
    val dealPrice: Double?,

    /**
     * 最高價
     */
    @SerializedName("HighestPrice")
    val highestPrice: Double?,

    /**
     * 最低價
     */
    @SerializedName("LowestPrice")
    val lowestPrice: Double?,

    /**
     * 該股票的跌停價
     */
    @SerializedName("LimitDown")
    val limitDown: String?,

    /**
     * 該股票的漲停價
     */
    @SerializedName("LimitUp")
    val limitUp: String?,

    /**
     * 開盤價
     */
    @SerializedName("OpenPrice")
    val openPrice: Double?,

    /**
     * 漲跌值
     */
    @SerializedName("PriceChange")
    val priceChange: Double?,

    /**
     * 百分比的漲跌幅
     */
    @SerializedName("QuoteChange")
    val quoteChange: Double?,

    /**
     * 本次單量
     */
    @SerializedName("SingleVolume")
    val singleVolume: ULong?,

    /**
     * 今日總交易量
     */
    @SerializedName("TotalVolume")
    val totalVolume: ULong?,

    /**
     * 交易時間(UnixTime)
     */
    @SerializedName("TickTime")
    val tickTime: Long?,

    /**
     * 狀態編碼(回傳下一次的Polling用)
     */
    @SerializedName("StatusCode")
    val statusCode: Int?,

    /**
     * 1-台股, 2-國際、台指、午後盤
     */
    @SerializedName("PackageDataType")
    val packageDataType: Int?,

    /**
     * 大戶散戶控盤狀態(0:中立 1: 大戶控盤 2:散戶控盤)
     */
    @SerializedName("InvestorStatus")
    val investorStatus: Int?
)