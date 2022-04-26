package com.cmoney.backend2.forumocean.service.api.article.createpersonal

import com.google.gson.annotations.SerializedName

/**
 * 創建個人文章response body
 *
 * @property articleId 創建之文章id
 */
data class CreatePersonalArticleResponseBody(
    @SerializedName("articleId")
    val articleId : Long?
)