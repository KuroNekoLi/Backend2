package com.cmoney.backend2.mobileocean.service.api.getforumpopulararticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetForumPopularArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<PopularArticle?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
) : CMoneyError(), IWithError<GetForumPopularArticlesResponse> {

    override fun toRealResponse(): GetForumPopularArticlesResponse {
        return GetForumPopularArticlesResponse(articles, updatedInSeconds)
    }
}