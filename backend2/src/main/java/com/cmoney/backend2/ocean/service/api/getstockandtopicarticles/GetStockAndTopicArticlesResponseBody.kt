package com.cmoney.backend2.ocean.service.api.getstockandtopicarticles

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetStockAndTopicArticlesResponseBody (
    @SerializedName("Articles")
    val articles : List<Article?>?
)