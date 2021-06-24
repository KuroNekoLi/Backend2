package com.cmoney.backend2.mobileocean.service.api.likearticle

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class LikeArticleResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<LikeArticleResponse> {

    override fun toRealResponse(): LikeArticleResponse {
        return LikeArticleResponse(isSuccess)
    }
}