package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo
import com.google.gson.annotations.SerializedName

data class CommentContentV2(
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("text")
    val text : String?,
    @SerializedName("multiMedia")
    val multiMedia : List<MediaTypeInfo>?,
)