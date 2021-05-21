package com.cmoney.backend2.customgroup.service.api.updatecustomlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UpdateCustomListCompleteWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(),
    IWithError<UpdateCustomListComplete> {

    override fun toRealResponse(): UpdateCustomListComplete {
        return UpdateCustomListComplete(
            isSuccess
        )
    }
}