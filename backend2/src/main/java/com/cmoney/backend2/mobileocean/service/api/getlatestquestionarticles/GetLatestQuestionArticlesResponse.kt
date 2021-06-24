package com.cmoney.backend2.mobileocean.service.api.getlatestquestionarticles

import com.cmoney.backend2.mobileocean.service.api.common.article.Article
import com.google.gson.annotations.SerializedName

data class GetLatestQuestionArticlesResponse(
    @SerializedName("Articles")
    val articles: List<Article?>?
)