package com.cmoney.backend2.ocean.service.api.getstocklatestarticle

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetStockLatestArticleResponse(
    @SerializedName("Articles")
    val articles : List<Article?>?
)