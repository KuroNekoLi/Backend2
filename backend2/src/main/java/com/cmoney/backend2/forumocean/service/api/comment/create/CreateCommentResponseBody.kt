package com.cmoney.backend2.forumocean.service.api.comment.create

import com.google.gson.annotations.SerializedName

data class CreateCommentResponseBody(
    @SerializedName("commentIndex")
    val commentIndex : Long?
)
