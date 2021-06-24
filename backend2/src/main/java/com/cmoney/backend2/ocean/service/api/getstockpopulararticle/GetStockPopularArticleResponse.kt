package com.cmoney.backend2.ocean.service.api.getstockpopulararticle

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetStockPopularArticleResponse(
    @SerializedName("Articles")
    val articles : List<Article?>?
)