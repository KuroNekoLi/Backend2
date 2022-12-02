package com.cmoney.backend2.forumocean.service.api.comment.hide

import com.google.gson.annotations.SerializedName

data class HideCommentRequestBody(
    @SerializedName("hide")
    val hide : Boolean?,
)
