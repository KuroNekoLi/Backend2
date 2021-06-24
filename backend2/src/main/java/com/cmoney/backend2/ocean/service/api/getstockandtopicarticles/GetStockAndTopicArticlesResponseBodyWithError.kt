package com.cmoney.backend2.ocean.service.api.getstockandtopicarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetStockAndTopicArticlesResponseBodyWithError (
    @SerializedName("Articles")
    val articles : List<Article?>?
): CMoneyError(), IWithError<GetStockAndTopicArticlesResponseBody> {
    override fun toRealResponse(): GetStockAndTopicArticlesResponseBody {
        return GetStockAndTopicArticlesResponseBody(articles)
    }
}