package com.cmoney.backend2.forumocean.service.api.article.createquestion

import com.google.gson.annotations.SerializedName

data class CreateQuestionResponseBody(
    @SerializedName("articleId")
    val articleId : Long?
)