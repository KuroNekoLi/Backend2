package com.cmoney.backend2.forumocean.service.api.variable.response.interactive

import com.google.gson.annotations.SerializedName

data class MemberEmojis(
    @SerializedName("memberEmojis")
    val memberEmojis: List<ReactionInfoV2>?,
)

data class ReactionInfoV2(
    @SerializedName("memberId")
    val memberId: Long?,
    @SerializedName("emoji")
    val emoji: Int?,
)
