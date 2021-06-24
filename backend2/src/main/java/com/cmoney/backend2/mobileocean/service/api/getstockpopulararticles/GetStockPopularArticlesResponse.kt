package com.cmoney.backend2.mobileocean.service.api.getstockpopulararticles

import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetStockPopularArticlesResponse(
    @SerializedName("Articles")
    val articles: List<Article?>?
)