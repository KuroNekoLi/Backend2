package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

enum class OceanArticleType(val value : Int) {

    /**
     * 一般發文
     *
     */
    @SerializedName("1")
    Article(1),

    /**
     * 提問發文
     *
     */
    @SerializedName("2")
    Question(2),

    /**
     * 一般回文
     *
     */
    @SerializedName("3")
    ArticleReply(3),

    /**
     * 問答回文
     *
     */
    @SerializedName("4")
    QuestionReply(4),

    /**
     * 轉推文章
     *
     */
    @SerializedName("5")
    RetweetArticle(5);
    //6
}