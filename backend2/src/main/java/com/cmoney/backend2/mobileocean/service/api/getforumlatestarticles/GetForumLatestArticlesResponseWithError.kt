package com.cmoney.backend2.mobileocean.service.api.getforumlatestarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetForumLatestArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<LatestArticle?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
) : CMoneyError(), IWithError<GetForumLatestArticlesResponse> {
    override fun toRealResponse(): GetForumLatestArticlesResponse {
        return GetForumLatestArticlesResponse(articles, updatedInSeconds)
    }
}