package com.cmoney.backend2.forumocean.service.api.article.getbanstate

import com.google.gson.annotations.SerializedName

/**
 * 是否被禁言
 *
 * @property isBan 是否被禁言
 */
data class GetBanStateResponseBody(
    @SerializedName("isBan")
    val isBan : Boolean?
)
