package com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail

import com.google.gson.annotations.SerializedName

data class StockDealDetail(
    @SerializedName("DealInfoSet")
    val dealInfoSet: List<DetailInfo>?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?,
    @SerializedName("TimeCode")
    val timeCode: Int?
)