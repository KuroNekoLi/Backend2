package com.cmoney.backend2.mobileocean.service.api.common

import com.google.gson.annotations.SerializedName

/**
 * 文章類型
 */
enum class ArticleType(val value : Byte) {
    /**
     * 一般文章
     */
    @SerializedName("1")
    GeneralArticle(1),

    /**
     * 發問文章
     */
    @SerializedName("2")
    Question(2),

    /**
     * 一般回文
     */
    @SerializedName("3")
    GeneralCommet(3),

    /**
     * 問答回文
     */
    @SerializedName("4")
    QuestionCommet(4),

    /**
     * 轉推文章
     */
    @SerializedName("5")
    RetweetArticle(5)
}