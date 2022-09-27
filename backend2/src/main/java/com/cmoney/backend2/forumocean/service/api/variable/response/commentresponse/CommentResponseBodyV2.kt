package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.google.gson.annotations.SerializedName
/**
 * 回文response
 *
 * @property comments 回文清單
 * @property remainCount 剩餘的回文數量
 * @property nextCommentIndex 下一次取回文的Index
 */
data class CommentResponseBodyV2(
    @SerializedName("comments")
    val comments: List<CommentContentV2>?,
    @SerializedName("remainCount")
    val remainCount: Int?,
    @SerializedName("nextCommentIndex")
    val nextCommentIndex: Long?
)
