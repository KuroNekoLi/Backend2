package com.cmoney.backend2.ocean.service.api.updateclubdescription

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UpdateClubDescriptionResponseBodyWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
): CMoneyError(), IWithError<UpdateClubDescriptionResponseBody> {
    override fun toRealResponse(): UpdateClubDescriptionResponseBody {
        return UpdateClubDescriptionResponseBody(isSuccess)
    }
}