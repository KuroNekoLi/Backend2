package com.cmoney.backend2.ocean.service.api.deleteclub

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class DeleteClubResponseBodyWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
): CMoneyError(), IWithError<DeleteClubResponseBody> {
    override fun toRealResponse(): DeleteClubResponseBody {
        return DeleteClubResponseBody(isSuccess)
    }
}