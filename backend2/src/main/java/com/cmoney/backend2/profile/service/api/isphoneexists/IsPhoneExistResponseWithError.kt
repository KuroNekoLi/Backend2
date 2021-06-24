package com.cmoney.backend2.profile.service.api.isphoneexists

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class IsPhoneExistResponseWithError(
    @SerializedName("result")
    val result: Boolean?
) : CMoneyError(), IWithError<IsPhoneExistResponse> {
    override fun toRealResponse(): IsPhoneExistResponse {
        return IsPhoneExistResponse(result)
    }
}
