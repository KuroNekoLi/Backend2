package com.cmoney.backend2.forumocean.service.api.article.update

import com.google.gson.annotations.SerializedName

data class UpdateArticleRequestBody(
    @SerializedName("item1")
    val updateItem : Any?,
    @SerializedName("item2")
    val deleteItem : List<String>
)