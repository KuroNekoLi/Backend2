package com.cmoney.backend2.forumocean.service.api.rank.getsolutionexpertrank

import com.google.gson.annotations.SerializedName
/**
 * 解題達人排行資訊
 *
 * @property creatorId memberID
 * @property bestSolutionScore 最佳解的總分數(每天計算兩週內的增長)
 * @property ranking 目前排行
 * @property lastRanking 上次排行
 */
data class SolutionExpertRankResponseBody(
    @SerializedName("creatorId")
    val creatorId :Long?,
    @SerializedName("bestSolutionScore")
    val bestSolutionScore : Int?,
    @SerializedName("ranking")
    val ranking :Int?,
    @SerializedName("lastRanking")
    val lastRanking :Int?
)
