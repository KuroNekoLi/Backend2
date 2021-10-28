package com.cmoney.backend2.ocean.service.api.isphoneauthentication


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class IsPhoneAuthenticationResponseBodyWithError(
    @SerializedName("Data")
    val data: List<PhoneAuthInfo>?
): CMoneyError(), IWithError<IsPhoneAuthenticationResponseBody> {
    override fun toRealResponse(): IsPhoneAuthenticationResponseBody {
        return IsPhoneAuthenticationResponseBody(data)
    }
}