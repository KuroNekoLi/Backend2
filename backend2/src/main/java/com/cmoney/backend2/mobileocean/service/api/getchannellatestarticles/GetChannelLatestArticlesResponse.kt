package com.cmoney.backend2.mobileocean.service.api.getchannellatestarticles


import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetChannelLatestArticlesResponse(
    @SerializedName("Articles")
    val articles: List<Article?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
)