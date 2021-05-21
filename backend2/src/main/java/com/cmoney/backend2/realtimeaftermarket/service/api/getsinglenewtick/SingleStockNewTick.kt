package com.cmoney.backend2.realtimeaftermarket.service.api.getsinglenewtick

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * Polling單檔股票即時Tick資訊
 *
 * @property askQty 外盤總量
 * @property averagePrice 平均價
 * @property bidQty 內盤總量
 * @property buyOrSell
 * @property buyPr1 委託Top1的買價
 * @property buyPr2 委託Top2的買價
 * @property buyPr3 委託Top3的買價
 * @property buyPr4 委託Top4的買價
 * @property buyPr5 委託Top5的買價
 * @property buyQty1 委託Top1的委託買量
 * @property buyQty2 委託Top2的委託買量
 * @property buyQty3 委託Top3的委託買量
 * @property buyQty4 委託Top4的委託買量
 * @property buyQty5 委託Top5的委託買量
 * @property ceilingPrice 最高價
 * @property chartData 畫走勢圖用的資料
 * @property closePrice 最終成交(收盤)價
 * @property commkey 股票編號
 * @property groupedPriceVolumeData 分價量及內外盤比資料(以價格做分組)
 * @property investorChartData 大戶散戶買賣超資料畫走勢圖用
 * @property isCloseMarket
 * @property isSuccess 要求是否成功
 * @property limitDown 該股票的今日漲停價
 * @property limitUp 該股票的今日跌停價
 * @property lowestPrice 最低價
 * @property marketTime 最後交易的市場時間(UnixTime)
 * @property openPrice 開盤價
 * @property packageDataType 1-完整資料, 2-股票有交易跳動時,只回傳需變動的資料(沒有委託Top5) , 3-股票有委託有變動時,只回傳需變動的資料(沒有股票的價量狀態)
 * @property prevClose 昨日收盤價
 * @property priceChange 漲跌值
 * @property quoteChange 漲跌幅
 * @property refPrice 參考價
 * @property responseCode 意料錯誤編碼( *100003-可能是comet逾時所以當正常)
 * @property responseMsg 意料錯誤訊息
 * @property sellPr1 委託Top1的賣價
 * @property sellPr2 委託Top2的賣價
 * @property sellPr3 委託Top3的賣價
 * @property sellPr4 委託Top4的賣價
 * @property sellPr5 委託Top5的賣價
 * @property sellQty1 委託Top1的委託賣量
 * @property sellQty2 委託Top2的委託賣量
 * @property sellQty3 委託Top3的委託賣量
 * @property sellQty4 委託Top4的委託賣量
 * @property sellQty5 委託Top5的委託賣量
 * @property statusCode 狀態編碼(回傳下一次的Polling用)，1:清盤 ; -1 : 暫停交易
 * @property tickQty 單量
 * @property totalQty 今日總量
 */
data class SingleStockNewTick(
    @SerializedName("AskQty")
    val askQty: Int?,
    @SerializedName("AveragePrice")
    val averagePrice: Double?,
    @SerializedName("BidQty")
    val bidQty: Int?,
    @SerializedName("BuyOrSell")
    val buyOrSell: String?,
    @SerializedName("BuyPr1")
    val buyPr1: Double?,
    @SerializedName("BuyPr2")
    val buyPr2: Double?,
    @SerializedName("BuyPr3")
    val buyPr3: Double?,
    @SerializedName("BuyPr4")
    val buyPr4: Double?,
    @SerializedName("BuyPr5")
    val buyPr5: Double?,
    @SerializedName("BuyQty1")
    val buyQty1: Int?,
    @SerializedName("BuyQty2")
    val buyQty2: Int?,
    @SerializedName("BuyQty3")
    val buyQty3: Int?,
    @SerializedName("BuyQty4")
    val buyQty4: Int?,
    @SerializedName("BuyQty5")
    val buyQty5: Int?,
    @SerializedName("CeilingPrice")
    val ceilingPrice: Double?,
    @SerializedName("ChartData")
    val chartData: List<ChartData?>?,
    @SerializedName("ClosePrice")
    val closePrice: Double?,
    @SerializedName("Commkey")
    val commkey: String?,
    @SerializedName("GroupedPriceVolumeData")
    val groupedPriceVolumeData: List<GroupedPriceVolumeData?>?,
    @SerializedName("InvestorChartData")
    val investorChartData: List<InvestorChartData?>?,
    @SerializedName("IsCloseMarket")
    val isCloseMarket: Boolean?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("LimitDown")
    val limitDown: Double?,
    @SerializedName("LimitUp")
    val limitUp: Double?,
    @SerializedName("LowestPrice")
    val lowestPrice: Double?,
    @SerializedName("MarketTime")
    val marketTime: Long?,
    @SerializedName("OpenPrice")
    val openPrice: Double?,
    @SerializedName("PackageDataType")
    val packageDataType: Int?,
    @SerializedName("PrevClose")
    val prevClose: Double?,
    @SerializedName("PriceChange")
    val priceChange: Double?,
    @SerializedName("QuoteChange")
    val quoteChange: Double?,
    @SerializedName("RefPrice")
    val refPrice: Double?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?,
    @SerializedName("SellPr1")
    val sellPr1: Double?,
    @SerializedName("SellPr2")
    val sellPr2: Double?,
    @SerializedName("SellPr3")
    val sellPr3: Double?,
    @SerializedName("SellPr4")
    val sellPr4: Double?,
    @SerializedName("SellPr5")
    val sellPr5: Double?,
    @SerializedName("SellQty1")
    val sellQty1: Int?,
    @SerializedName("SellQty2")
    val sellQty2: Int?,
    @SerializedName("SellQty3")
    val sellQty3: Int?,
    @SerializedName("SellQty4")
    val sellQty4: Int?,
    @SerializedName("SellQty5")
    val sellQty5: Int?,
    @SerializedName("StatusCode")
    val statusCode: String?,
    @SerializedName("TickQty")
    val tickQty: Int?,
    @SerializedName("TotalQty")
    val totalQty: Int?
): ISuccess {
    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 0 || responseCode == 100003  // 100003-可能是comet逾時所以當正常
    }
}