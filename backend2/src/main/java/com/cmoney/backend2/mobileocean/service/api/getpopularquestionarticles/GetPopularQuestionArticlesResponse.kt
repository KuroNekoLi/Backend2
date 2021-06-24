package com.cmoney.backend2.mobileocean.service.api.getpopularquestionarticles

import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetPopularQuestionArticlesResponse(
    @SerializedName("Articles")
    val articles: List<Article?>?
)