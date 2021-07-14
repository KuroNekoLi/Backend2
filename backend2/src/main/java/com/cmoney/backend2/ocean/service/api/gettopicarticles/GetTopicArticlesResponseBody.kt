package com.cmoney.backend2.ocean.service.api.gettopicarticles

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class GetTopicArticlesResponseBody (
    @SerializedName("Articles")
    val articles : List<Article?>?
)