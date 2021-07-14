package com.cmoney.backend2.realtimeaftermarket.service.api.getInternationalTicks

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * @property commKey 股票代號
 * @property refPr 參考價
 * @property openPrice 開盤價
 * @property ceilingPrice 最高價
 * @property lowestPrice 最低價
 * @property prevClose 昨收
 * @property salePr 成交價
 * @property marketTime 最後交易的市場時間(UnixTime)
 * @property totalQty 成交總量
 * @property priceChange 漲跌
 * @property quoteChange 漲跌幅
 * @property statusCode 狀態編碼(回傳下一次的Polling用)，1:清盤 ; -1 : 暫停交易
 * @property chartData 線圖資料
 * @property startTime 開盤時間(UnixTime)
 * @property endTime 收盤時間(UnixTime)
 * @property isSuccess 是否沒有意料中的錯誤
 * @property responseCode 意料錯誤編碼( *100003-可能是comet逾時所以當正常)
 * @property responseMsg 意料錯誤訊息
 */
data class InternationalNewTicks(
    @SerializedName("Commkey")
    val commKey: String?,
    @SerializedName("RefPr")
    val refPr: String?,
    @SerializedName("OpenPrice")
    val openPrice: Double?,
    @SerializedName("CeilingPrice")
    val ceilingPrice: Double?,
    @SerializedName("LowestPrice")
    val lowestPrice: Double?,
    @SerializedName("PrevClose")
    val prevClose: Double?,
    @SerializedName("SalePr")
    val salePr: Double?,
    @SerializedName("MarketTime")
    val marketTime: Long?,
    @SerializedName("TotalQty")
    val totalQty: Long?,
    @SerializedName("PriceChange")
    val priceChange: Double?,
    @SerializedName("QuoteChange")
    val quoteChange: Double?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("ChartData")
    val chartData: List<InternationalChartData>?,
    @SerializedName("StartTime")
    val startTime: Long?,
    @SerializedName("EndTime")
    val endTime: Long?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
): ISuccess {
    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 0 || responseCode == 100003  // 100003-可能是comet逾時所以當正常
    }
}