package com.cmoney.backend2.forumocean.service.api.rating


import com.google.gson.annotations.SerializedName

/**
 * 被評價Object
 *
 * @param comment 評語
 * @param memberId 用戶ID
 * @param score 評分
 * @param updateTime 更新時間
 */
data class OthersRatingComment(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("memberId")
    val memberId: Long?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("updateTime")
    val updateTime: Long?
)