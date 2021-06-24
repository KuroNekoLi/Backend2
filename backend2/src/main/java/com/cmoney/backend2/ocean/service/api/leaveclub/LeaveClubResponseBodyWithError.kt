package com.cmoney.backend2.ocean.service.api.leaveclub

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class LeaveClubResponseBodyWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
): CMoneyError(), IWithError<LeaveClubResponseBody> {
    override fun toRealResponse(): LeaveClubResponseBody {
        return LeaveClubResponseBody(isSuccess)
    }
}