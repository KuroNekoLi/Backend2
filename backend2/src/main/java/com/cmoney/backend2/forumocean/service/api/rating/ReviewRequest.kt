package com.cmoney.backend2.forumocean.service.api.rating


import com.google.gson.annotations.SerializedName

/**
 * Request object for reviewing a user.
 *
 * @param comment 評語
 * @param score 評分
 * @param toMemberId 被評分用戶memberId
 */
data class ReviewRequest(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("toMemberId")
    val toMemberId: Long?
)