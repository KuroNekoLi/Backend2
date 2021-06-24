package com.cmoney.backend2.mobileocean.service.api.givearticletip

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GiveArticleTipResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<GiveArticleTipResponse> {

    override fun toRealResponse(): GiveArticleTipResponse {
        return GiveArticleTipResponse(isSuccess)
    }
}