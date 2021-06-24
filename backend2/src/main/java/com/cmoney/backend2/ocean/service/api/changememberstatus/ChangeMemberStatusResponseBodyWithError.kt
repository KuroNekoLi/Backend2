package com.cmoney.backend2.ocean.service.api.changememberstatus

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class ChangeMemberStatusResponseBodyWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
): CMoneyError(), IWithError<ChangeMemberStatusResponseBody> {
    override fun toRealResponse(): ChangeMemberStatusResponseBody {
        return ChangeMemberStatusResponseBody(isSuccess)
    }
}