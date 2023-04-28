package com.cmoney.backend2.forumocean.service.api.article.create

import com.google.gson.annotations.SerializedName

/**
 * Note: Multiple endpoints which also create Articles use this dto.
 */
data class CreateArticleResponseBody(
    @SerializedName("articleId")
    val articleId : Long?
)