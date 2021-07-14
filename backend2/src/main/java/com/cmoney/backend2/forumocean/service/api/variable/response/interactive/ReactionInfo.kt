package com.cmoney.backend2.forumocean.service.api.variable.response.interactive

import com.google.gson.annotations.SerializedName

/**
 * 互動詳細訊息
 *
 * @property memberId
 * @property reactionType
 * @property time
 */
data class ReactionInfo(
    @SerializedName("memberId")
    val memberId : Long?,
    @SerializedName("reactionType")
    val reactionType : Int?,
    @SerializedName("time")
    val time : Long?
)