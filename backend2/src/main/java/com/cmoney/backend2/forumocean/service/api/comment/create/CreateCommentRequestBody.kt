package com.cmoney.backend2.forumocean.service.api.comment.create

import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.google.gson.annotations.SerializedName

data class CreateCommentRequestBody(
    @SerializedName("text")
    val text : String?,
    @SerializedName("multimedia")
    val multiMedia : List<MediaType>?,
    @SerializedName("position")
    val position : Any?
)