package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * @SerializedName("askPoint")
 * val askPoint : Int?
 * @SerializedName("bestAnswerCommentId")
 * val bestAnswerCommentId : Long?
 */
interface QuestionInfo {
    /**
     * 問答支付P幣
     */
    val askPoint : Int?

    /**
     * 最佳解答回文ID
     */
    val bestAnswerCommentId : Long?
}