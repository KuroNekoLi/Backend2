package com.cmoney.backend2.identityprovider.service.api.islatest

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class IsLatestResponseBodyWithError(
    @SerializedName("result")
    val isSuccess: Boolean
) : CMoneyError(), IWithError<IsLatestResponseBody> {
    override fun toRealResponse(): IsLatestResponseBody {
        return IsLatestResponseBody(isSuccess)
    }
}