package com.cmoney.backend2.forumocean.service.api.rank.getfansmemberrank

import com.google.gson.annotations.SerializedName
/**
 * 粉絲達人排行資訊
 *
 * @property followMemberId memberID
 * @property fansIncreaseCount 粉絲增加數(每天計算兩週內的增長)
 * @property ranking 目前排行
 * @property lastRanking 上次排行
 */
data class FansMemberRankResponseBody(
    @SerializedName("followMemberId")
    val followMemberId :Long?,
    @SerializedName("fansIncreaseCount")
    val fansIncreaseCount : Int?,
    @SerializedName("ranking")
    val ranking :Int?,
    @SerializedName("lastRanking")
    val lastRanking :Int?
)