package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 回文相關欄位
 * 請加入以下欄位
 * @SerializedName("comment")
 * val myCommentIndex : List<Int>?
 * @SerializedName("@list-comment")
 * val commentCount : Int?
 */
interface CommentInfo {
    /**
     * 我的回文位置
     */
    val myCommentIndex : List<Int>?

    /**
     * 總回文數
     */
    val commentCount : Int?
}