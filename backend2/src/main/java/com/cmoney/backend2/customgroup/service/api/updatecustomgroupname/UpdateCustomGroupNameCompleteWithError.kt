package com.cmoney.backend2.customgroup.service.api.updatecustomgroupname

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UpdateCustomGroupNameCompleteWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(),
    IWithError<UpdateCustomGroupNameComplete> {

    override fun toRealResponse(): UpdateCustomGroupNameComplete {
        return UpdateCustomGroupNameComplete(
            isSuccess
        )
    }
}