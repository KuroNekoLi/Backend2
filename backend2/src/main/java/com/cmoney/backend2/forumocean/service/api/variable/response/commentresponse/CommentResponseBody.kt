package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.google.gson.annotations.SerializedName

data class CommentResponseBody(
    @SerializedName("id")
    val id : Long?,
    @SerializedName("content")
    val content: CommentContent?
)