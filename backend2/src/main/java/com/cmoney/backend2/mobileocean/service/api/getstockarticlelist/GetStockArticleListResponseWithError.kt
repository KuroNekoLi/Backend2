package com.cmoney.backend2.mobileocean.service.api.getstockarticlelist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetStockArticleListResponseWithError(
    @SerializedName("Articles")
    val articles: List<Article?>?
) : CMoneyError(), IWithError<GetStockArticleListResponse> {

    override fun toRealResponse(): GetStockArticleListResponse {
        return GetStockArticleListResponse(articles)
    }
}