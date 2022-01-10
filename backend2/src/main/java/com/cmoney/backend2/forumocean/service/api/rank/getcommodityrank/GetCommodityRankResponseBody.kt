package com.cmoney.backend2.forumocean.service.api.rank.getcommodityrank

import com.google.gson.annotations.SerializedName

/**
 * 個股排行資訊
 *
 * @property commodityKey 股票代號
 * @property score 熱門值
 * @property ranking 目前排行
 * @property lastRanking 上次排行
 */
data class GetCommodityRankResponseBody(
    @SerializedName("commodityKey")
    val commodityKey :String?,
    @SerializedName("score")
    val score : Long?,
    @SerializedName("ranking")
    val ranking :Int?,
    @SerializedName("lastRanking")
    val lastRanking :Int?
)
