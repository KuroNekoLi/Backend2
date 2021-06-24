package com.cmoney.backend2.mobileocean.service.api.leavechannel

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class LeaveChannelResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<LeaveChannelResponse> {

    override fun toRealResponse(): LeaveChannelResponse {
        return LeaveChannelResponse(isSuccess)
    }
}