package com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class AddInterestedInArticleInfoResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<AddInterestedInArticleInfoResponse> {

    override fun toRealResponse(): AddInterestedInArticleInfoResponse {
        return AddInterestedInArticleInfoResponse(isSuccess)
    }
}