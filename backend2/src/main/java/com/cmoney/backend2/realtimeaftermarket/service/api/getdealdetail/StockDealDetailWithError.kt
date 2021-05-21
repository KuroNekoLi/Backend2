package com.cmoney.backend2.realtimeaftermarket.service.api.getdealdetail

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class StockDealDetailWithError(
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
) : IWithError<StockDealDetail>, CMoneyError()
{
    override fun toRealResponse(): StockDealDetail {
        return StockDealDetail(
            dealInfoSet = dealInfoSet,
            isSuccess = isSuccess,
            responseCode = responseCode,
            responseMsg = responseMsg,
            timeCode = timeCode
        )
    }
}