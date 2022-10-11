package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.google.gson.annotations.SerializedName

/**
 * 回文內容v2
 */
data class CommentResponseBodyV2(
    @SerializedName("id")
    val id: String?,
    @SerializedName("content")
    val content: CommentContentV2?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("modifyTime")
    val modifyTime: Long?,
    @SerializedName("isHidden")
    val isHidden: Boolean?,
    @SerializedName("myReaction")
    val myReaction: String?,
    @SerializedName("reaction")
    val reaction: Map<String, Int>?,
    @SerializedName("@list-reaction")
    val reactionCount: Int?,
    @SerializedName("myComments")
    val myCommentIndex: List<Int>?,
    @SerializedName("commentCount")
    val commentCount: Int?,
    @SerializedName("hasReport")
    val report: Any?,
)
