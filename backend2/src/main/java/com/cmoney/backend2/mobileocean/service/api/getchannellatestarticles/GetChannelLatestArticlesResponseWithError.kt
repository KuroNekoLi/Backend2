package com.cmoney.backend2.mobileocean.service.api.getchannellatestarticles

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetChannelLatestArticlesResponseWithError(
    @SerializedName("Articles")
    val articles: List<Article?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
) : CMoneyError(), IWithError<GetChannelLatestArticlesResponse> {

    override fun toRealResponse(): GetChannelLatestArticlesResponse {
        return GetChannelLatestArticlesResponse(
            articles,
            updatedInSeconds
        )
    }
}