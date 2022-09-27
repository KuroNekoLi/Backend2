package com.cmoney.backend2.forumocean.service.api.comment.create

import com.google.gson.annotations.SerializedName

data class CreateCommentResponseBodyV2(
    @SerializedName("commentIndex")
    val commentIndex : Long?
)
