package com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics

import com.google.gson.annotations.SerializedName

/**
 * 指定會員的統計資訊
 *
 * @property memberId
 * @property totalCountArticle 總發文數
 * @property totalCountReaction 總反應數
 * @property totalCountFollowing 總追蹤數
 * @property totalCountFollower 總被追蹤數
 */
data class GetMemberStatisticsResponseBody(
    @SerializedName("memberId")
    val memberId : Long?,
    @SerializedName("totalCountArticle")
    val totalCountArticle : Int?,
    @SerializedName("totalCountReaction")
    val totalCountReaction : Int?,
    @SerializedName("totalCountFollowing")
    val totalCountFollowing : Int?,
    @SerializedName("totalCountFollower")
    val totalCountFollower: Int?,
    @SerializedName("isBan")
    val isBan: Boolean?
)
