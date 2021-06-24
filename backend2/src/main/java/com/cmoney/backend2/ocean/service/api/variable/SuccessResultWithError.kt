package com.cmoney.backend2.ocean.service.api.variable

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class SuccessResultWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(),
    IWithError<SuccessResult> {
    override fun toRealResponse() =
        SuccessResult(isSuccess)
}