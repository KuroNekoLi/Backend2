package com.cmoney.backend2.mobileocean.service.api.getchannelpopulararticles

import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetChannelPopularArticlesResponse(
    @SerializedName("Articles")
    val articles: List<Article>?
)