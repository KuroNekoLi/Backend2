package com.cmoney.backend2.forumocean.service.api.rating


import com.google.gson.annotations.SerializedName

/**
 * 評價
 *
 * @param comment 註解
 * @param score 評多少分
 * @param reviewTime 什麼時候評分
 */
data class RatingComment(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("reviewTime")
    val reviewTime: Long?
)
