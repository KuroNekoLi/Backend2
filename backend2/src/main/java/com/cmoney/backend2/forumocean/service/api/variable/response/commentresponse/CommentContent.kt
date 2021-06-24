package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo
import com.google.gson.annotations.SerializedName

data class CommentContent(
    @SerializedName("creatorId")
    val creatorId : Long?,
    @SerializedName("text")
    val text : String?,
    @SerializedName("multiMedia")
    val multiMedia : List<MediaTypeInfo>?,
    @SerializedName("position")
    val position : Any?,
    @SerializedName("commentState")
    val commentState : CommentState?,
    @SerializedName("reactionState")
    val reactionState : Int?,
    @SerializedName("reactions")
    val reaction : Map<String,Int>?,
    @SerializedName("createTime")
    val createTime : Long?,
    @SerializedName("modifyTime")
    val modifyTime : Long?
)