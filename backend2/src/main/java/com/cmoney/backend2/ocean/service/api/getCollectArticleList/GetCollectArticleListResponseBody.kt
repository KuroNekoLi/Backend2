package com.cmoney.backend2.ocean.service.api.getCollectArticleList

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetCollectArticleListResponseBody (
    @SerializedName("Articles")
    val articles : List<Article?>?
)