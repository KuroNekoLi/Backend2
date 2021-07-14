package com.cmoney.backend2.realtimeaftermarket.service.api.getforeignexchangeticks


import com.google.gson.annotations.SerializedName

/**
 * 外匯即時回傳物件
 *
 * @property isMarketClosed 文件未描述
 * @property isSuccess 文件未描述
 * @property responseCode 文件未描述
 * @property responseMsg 文件未描述
 * @property tickInfoSet 即時資料
 * @constructor Create empty Foreign exchange tick response body
 */
data class GetForeignExchangeTickResponseBody(
    @SerializedName("IsMarketClosed")
    val isMarketClosed: Boolean?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?,
    @SerializedName("TickInfoSet")
    val tickInfoSet: List<TickInfo>?
)