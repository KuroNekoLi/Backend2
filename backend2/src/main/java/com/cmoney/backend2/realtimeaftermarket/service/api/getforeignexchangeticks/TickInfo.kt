package com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks


import com.google.gson.annotations.SerializedName

/**
 * 外匯即時Tick
 *
 * @property buyOrSell 內外盤(內盤:0 外盤:1)
 * @property commKey 商品代號
 * @property dealPrice 交易價格
 * @property highestPrice 未知(文件中未有)
 * @property investorStatus 未知(文件中未有)
 * @property limitDown 跌停價
 * @property limitUp 漲停價
 * @property lowestPrice 未知(文件中未有)
 * @property openPrice 未知(文件中未有)
 * @property packageDataType 資料型別
 * @property priceChange 漲跌價
 * @property quoteChange 漲跌幅
 * @property refPrice 參考價
 * @property singleVolume 成交單量
 * @property statusCode 狀態編碼(回傳下一次的Polling用)，1:清盤 ; -1 : 暫停交易
 * @property tickTime 交易時間(秒)
 * @property totalVolume 成交總量
 */
data class TickInfo(
    @SerializedName("BuyOrSell")
    val buyOrSell: Int?,
    @SerializedName("Commkey")
    val commKey: String?,
    @SerializedName("DealPrice")
    val dealPrice: Double?,
    @SerializedName("HighestPrice")
    val highestPrice: Double?,
    @SerializedName("InvestorStatus")
    val investorStatus: Int?,
    @SerializedName("LimitDown")
    val limitDown: String?,
    @SerializedName("LimitUp")
    val limitUp: String?,
    @SerializedName("LowestPrice")
    val lowestPrice: Double?,
    @SerializedName("OpenPrice")
    val openPrice: Double?,
    @SerializedName("PackageDataType")
    val packageDataType: Int?,
    @SerializedName("PriceChange")
    val priceChange: Double?,
    @SerializedName("QuoteChange")
    val quoteChange: Double?,
    @SerializedName("RefPrice")
    val refPrice: Double?,
    @SerializedName("SingleVolume")
    val singleVolume: Int?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("TickTime")
    val tickTime: Long?,
    @SerializedName("TotalVolume")
    val totalVolume: Int?
)