package com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UpdateCustomGroupOrderCompleteWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(),
    IWithError<UpdateCustomGroupOrderComplete> {
    override fun toRealResponse(): UpdateCustomGroupOrderComplete {
        return UpdateCustomGroupOrderComplete(
            isSuccess
        )
    }
}