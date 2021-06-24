package com.cmoney.backend2.mobileocean.service.api.createarticle.responsebody

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CreateArticleResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,

    @SerializedName("ResponseCode")
    val responseCode: Int?,

    @SerializedName("NewArticleId")
    val newArticleId: Long?
) : CMoneyError(), IWithError<CreateArticleResponse> {

    override fun toRealResponse(): CreateArticleResponse {
        return CreateArticleResponse(isSuccess, responseCode, newArticleId)
    }
}