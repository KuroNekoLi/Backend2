package com.cmoney.backend2.identityprovider.service.api.revoke


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class RevokeResponseBodyWithError(
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("success")
    val success: Boolean?
) : CMoneyError(), IWithError<RevokeResponseBody> {

    override fun toRealResponse(): RevokeResponseBody {
        return RevokeResponseBody(tokenType, success)
    }
}