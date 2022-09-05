package com.cmoney.backend2.forumocean.service.api.rating


import com.google.gson.annotations.SerializedName

/**
 * 被評價資訊統計
 *
 * @param rating 分數
 * @param reviewCount 被評價次數
 */
data class MemberRatingCounter(
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("reviewCount")
    val reviewCount: Int?
)