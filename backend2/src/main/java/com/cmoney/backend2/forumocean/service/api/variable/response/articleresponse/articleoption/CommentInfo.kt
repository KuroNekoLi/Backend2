package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 回文相關欄位
 * 請加入以下欄位
 * @SerializedName("@value-commentDeleted")
 * val commentDeletedCount : Int?
 * @SerializedName("comment", alternate = ["myComments"])
 * val myCommentIndex : List<Int>?
 * @SerializedName("@list-comment", alternate = ["commentCount"])
 * val commentCount : Int?
 */
interface CommentInfo {
    /**
     * 被刪除的回文數
     */
    val commentDeletedCount: Int?

    /**
     * 我的回文位置
     */
    val myCommentIndex: List<Int>?

    /**
     * 總回文數
     */
    val commentCount: Int?
}