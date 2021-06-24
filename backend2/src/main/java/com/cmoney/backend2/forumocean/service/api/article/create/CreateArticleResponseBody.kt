package com.cmoney.backend2.forumocean.service.api.article.create

import com.google.gson.annotations.SerializedName

data class CreateArticleResponseBody(
    @SerializedName("articleId")
    val articleId : Long?
)