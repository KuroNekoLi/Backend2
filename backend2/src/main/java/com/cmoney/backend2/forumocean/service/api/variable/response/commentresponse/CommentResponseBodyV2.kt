package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.google.gson.annotations.SerializedName

/**
 * 回文內容v2
 */
data class CommentResponseBodyV2(
    @SerializedName("id")
    val id: String?,
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("content")
    val content: CommentContentV2?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("modifyTime")
    val modifyTime: Long?,
    @SerializedName("isHidden")
    val isHidden: Boolean?,
    @SerializedName("myEmoji")
    val myEmoji: String?,
    @SerializedName("emojiCount")
    val emojiCount: Map<String, Int>?,
    @SerializedName("myComments")
    val myCommentIndex: List<Int>?,
    @SerializedName("commentCount")
    val commentCount: Int?,
    @SerializedName("hasReport")
    val hasReport: Boolean?,
)
