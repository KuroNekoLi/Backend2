package com.cmoney.backend2.mobileocean.service.api.getchannelpopulararticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetChannelPopularArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<Article>?
) : CMoneyError(), IWithError<GetChannelPopularArticlesResponse>{
    override fun toRealResponse(): GetChannelPopularArticlesResponse {
        return GetChannelPopularArticlesResponse(articles)
    }
}