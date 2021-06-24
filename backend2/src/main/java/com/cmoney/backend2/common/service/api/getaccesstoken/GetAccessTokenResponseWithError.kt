package com.cmoney.backend2.common.service.api.getaccesstoken


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetAccessTokenResponseWithError(
    @SerializedName("AccessToken")
    val accessToken: String?
) : CMoneyError(), IWithError<GetAccessToken> {
    override fun toRealResponse(): GetAccessToken {
        return GetAccessToken(accessToken)
    }
}