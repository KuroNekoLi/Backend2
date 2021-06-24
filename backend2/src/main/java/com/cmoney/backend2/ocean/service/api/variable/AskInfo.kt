package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

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