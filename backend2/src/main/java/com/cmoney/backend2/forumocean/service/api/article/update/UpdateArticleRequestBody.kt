package com.cmoney.backend2.forumocean.service.api.article.update

import com.google.gson.annotations.SerializedName

data class UpdateArticleRequestBody(
    @SerializedName("article")
    val updateItem : Any?,
    @SerializedName("removeProperties")
    val deleteItem : List<String>
)