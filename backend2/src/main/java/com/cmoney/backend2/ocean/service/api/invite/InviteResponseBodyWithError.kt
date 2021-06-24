package com.cmoney.backend2.ocean.service.api.invite

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class InviteResponseBodyWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
): CMoneyError(), IWithError<InviteResponseBody> {
    override fun toRealResponse(): InviteResponseBody {
        return InviteResponseBody(isSuccess)
    }
}