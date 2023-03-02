package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.spacepin

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBodyV2
import com.google.gson.annotations.SerializedName

data class GetSpaceBoardPinArticlesResponseBody(
    @SerializedName("articles")
    val articles: List<ArticleResponseBodyV2>
)
