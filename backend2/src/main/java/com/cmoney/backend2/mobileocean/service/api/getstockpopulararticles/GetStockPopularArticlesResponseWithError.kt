package com.cmoney.backend2.mobileocean.service.api.getstockpopulararticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetStockPopularArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<Article?>?
) : CMoneyError(), IWithError<GetStockPopularArticlesResponse> {
    override fun toRealResponse() =
        GetStockPopularArticlesResponse(articles)
}