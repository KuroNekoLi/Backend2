package com.cmoney.backend2.realtimeaftermarket.service.api.getmarketnewtick

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * @property isMarketClosed 是否收盤
 * @property tickInfoSet 有與狀態編碼不同的股票編號(Array)
 * @property isSuccess 是否沒有意料中的錯誤
 * @property responseCode 意料錯誤編碼( *100003-可能是comet逾時所以當正常)
 * @property responseMsg 意料錯誤訊息
 */
data class MarketNewTick (
    @SerializedName("IsMarketClosed")
    val isMarketClosed : Boolean?,
    @SerializedName("TickInfoSet")
    val tickInfoSet : List<TickInfoSet>?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("ResponseCode")
    val responseCode : Int?,
    @SerializedName("ResponseMsg")
    val responseMsg : String?
) : ISuccess {
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