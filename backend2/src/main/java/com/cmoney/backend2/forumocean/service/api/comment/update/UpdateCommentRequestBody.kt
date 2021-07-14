package com.cmoney.backend2.forumocean.service.api.comment.update

import com.google.gson.annotations.SerializedName

data class UpdateCommentRequestBody(
    @SerializedName("item1")
    val updateItem : Any?,
    @SerializedName("item2")
    val deleteItem : List<String>
)