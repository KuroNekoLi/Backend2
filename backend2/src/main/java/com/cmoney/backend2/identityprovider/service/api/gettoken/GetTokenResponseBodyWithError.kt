package com.cmoney.backend2.identityprovider.service.api.gettoken


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetTokenResponseBodyWithError(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("expires_in")
    val expiresIn: Int?,
    @SerializedName("id_token")
    val idToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("token_type")
    val tokenType: String?
) : CMoneyError(), IWithError<GetTokenResponseBody> {

    override fun toRealResponse(): GetTokenResponseBody {
        return GetTokenResponseBody(
            accessToken = accessToken,
            expiresIn = expiresIn,
            idToken = idToken,
            refreshToken = refreshToken,
            tokenType = tokenType
        )
    }
}