package com.cmoney.backend2.customgroup.service.api.deletecustomgroup

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class DeleteCustomGroupCompleteWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(),
    IWithError<DeleteCustomGroupComplete> {

    override fun toRealResponse(): DeleteCustomGroupComplete {
        return DeleteCustomGroupComplete(
            isSuccess
        )
    }
}