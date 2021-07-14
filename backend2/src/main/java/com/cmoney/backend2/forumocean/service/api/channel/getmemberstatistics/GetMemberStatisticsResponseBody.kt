package com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics

import com.google.gson.annotations.SerializedName

/**
 * 指定會員的統計資訊
 *
 * @property totalCountArticle 總發文數
 * @property totalCountReaction 總反應數
 */
data class GetMemberStatisticsResponseBody(
    @SerializedName("totalCountArticle")
    val totalCountArticle : Int?,
    @SerializedName("totalCountReaction")
    val totalCountReaction : Int?
)
