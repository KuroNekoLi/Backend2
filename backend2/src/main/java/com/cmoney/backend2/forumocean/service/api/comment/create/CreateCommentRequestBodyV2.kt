package com.cmoney.backend2.forumocean.service.api.comment.create

import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.google.gson.annotations.SerializedName

data class CreateCommentRequestBodyV2(
    @SerializedName("text")
    val text : String?,
    @SerializedName("multiMedia")
    val multiMedia : List<MediaType>?
)
