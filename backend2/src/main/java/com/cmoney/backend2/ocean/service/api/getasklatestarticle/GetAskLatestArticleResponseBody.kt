package com.cmoney.backend2.ocean.service.api.getasklatestarticle

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetAskLatestArticleResponseBody(
    @SerializedName("Articles")
    val articles : List<Article>?
)