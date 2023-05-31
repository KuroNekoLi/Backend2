package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的EmojiCount
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class EmojiCount(
    @SerializedName("like")
    var like: Int?,
    @SerializedName("dislike")
    var dislike: Int?,
    @SerializedName("laugh")
    var laugh: Int?,
    @SerializedName("money")
    var money: Int?,
    @SerializedName("shock")
    var shock: Int?,
    @SerializedName("cry")
    var cry: Int?,
    @SerializedName("think")
    var think: Int?,
    @SerializedName("angry")
    var angry: Int?
)