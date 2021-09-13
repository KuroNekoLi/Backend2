package com.cmoney.backend2.forumocean.service.api.rank.getexpertmemberrank

import com.google.gson.annotations.SerializedName

/**
 * 達人排行資訊
 *
 * @property creatorId memberID
 * @property score 熱門值
 * @property ranking 目前排行
 * @property lastRanking 上次排行
 */
data class GetExpertMemberRankResponseBody(
    @SerializedName("creatorId")
    val creatorId :Long?,
    @SerializedName("score")
    val score : Double?,
    @SerializedName("ranking")
    val ranking :Int?,
    @SerializedName("lastRanking")
    val lastRanking :Int?
)