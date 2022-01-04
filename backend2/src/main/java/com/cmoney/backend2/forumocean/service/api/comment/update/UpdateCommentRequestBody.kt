package com.cmoney.backend2.forumocean.service.api.comment.update

import com.google.gson.annotations.SerializedName

data class UpdateCommentRequestBody(
    @SerializedName("comment")
    val updateItem : Any?,
    @SerializedName("removeProperties")
    val deleteItem : List<String>
)