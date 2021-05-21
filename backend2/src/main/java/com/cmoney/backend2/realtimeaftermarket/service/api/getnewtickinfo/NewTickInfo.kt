package com.cmoney.backend2.realtimeaftermarket.service.api.getnewtickinfo

import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

data class NewTickInfo(
    @SerializedName("IsMarketClosed")
    val isMarketClosed: Boolean?,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?,
    @SerializedName("TickInfoSet")
    val tickInfoSet: List<TickInfoSet>?
): ISuccess {
    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 0 || responseCode == 100003
    }
}