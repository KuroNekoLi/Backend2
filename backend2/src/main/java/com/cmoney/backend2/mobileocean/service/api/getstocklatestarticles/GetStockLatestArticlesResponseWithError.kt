package com.cmoney.backend2.mobileocean.service.api.getstocklatestarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetStockLatestArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<Article?>?
) : CMoneyError(),
    IWithError<GetStockLatestArticlesResponse> {
    override fun toRealResponse(): GetStockLatestArticlesResponse {
        return GetStockLatestArticlesResponse(articles)
    }

}