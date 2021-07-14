package com.cmoney.backend2.ocean.service.api.gettopicarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetTopicArticlesResponseBodyWithError (
    @SerializedName("Articles")
    val articles : List<Article?>?
): CMoneyError(), IWithError<GetTopicArticlesResponseBody> {
    override fun toRealResponse(): GetTopicArticlesResponseBody {
        return GetTopicArticlesResponseBody(articles)
    }
}