package com.cmoney.backend2.mobileocean.service.api.getarticletips

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class GetArticleTipsResponseWithError(
    /**
     * 打賞清單
     */
    @SerializedName("Tips")
    val tips : List<Tip>
) : CMoneyError(),IWithError<GetArticleTipsResponse>{
    override fun toRealResponse(): GetArticleTipsResponse {
        return GetArticleTipsResponse(tips)
    }
}