package com.cmoney.backend2.mobileocean.service.api.common.article


import com.google.gson.annotations.SerializedName

/**
 * 問答文章資訊
 *
 * @property askMemberChannelId 提問者頻道編號
 * @property askPoint 提問付出的點數
 * @property bestAnswerArticleId 最佳解的文章編號 -1:還沒出現最佳解   0: 沒有最佳解
 * @property interestedCount 也想知道回答的人數
 * @property totalPoint 此問答文章價值點數
 */
data class AskInfo(
    @SerializedName("AskMemberChannelId")
    val askMemberChannelId: Long?,
    @SerializedName("AskPoint")
    val askPoint: Int?,
    @SerializedName("BestAnswerArticleId")
    val bestAnswerArticleId: Long?,
    @SerializedName("InterestedCount")
    val interestedCount: Int?,
    @SerializedName("TotalPoint")
    val totalPoint: Int?
)