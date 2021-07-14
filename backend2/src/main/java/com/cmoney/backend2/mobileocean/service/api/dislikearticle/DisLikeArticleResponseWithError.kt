package com.cmoney.backend2.mobileocean.service.api.dislikearticle

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class DisLikeArticleResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<DisLikeArticleResponse> {

    override fun toRealResponse(): DisLikeArticleResponse {
        return DisLikeArticleResponse(isSuccess)
    }
}