package com.cmoney.backend2.mobileocean.service.api.getstocklatestarticles

import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetStockLatestArticlesResponse(
    @SerializedName("Articles")
    val articles: List<Article?>?
)