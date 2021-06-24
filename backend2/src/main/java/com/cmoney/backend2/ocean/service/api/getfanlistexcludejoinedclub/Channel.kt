package com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub

import com.cmoney.backend2.ocean.service.api.variable.DiamondInfo
import com.cmoney.backend2.ocean.service.api.variable.EvaluationInfo
import com.cmoney.backend2.ocean.service.api.variable.LevelInfo
import com.cmoney.backend2.ocean.service.api.variable.ViewerEvaluationInfo
import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("AnswersCount")
    val answersCount: Int?,
    @SerializedName("ArticleCount")
    val articleCount: Int?,
    @SerializedName("AttentionCount")
    val attentionCount: Int?,
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("DiamondInfo")
    val diamondInfo: DiamondInfo?,
    @SerializedName("EvaluationInfo")
    val evaluationInfo: EvaluationInfo?,
    @SerializedName("FansCount")
    val fansCount: Int?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("IsFollowed")
    val isFollowed: Boolean?,
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("LikeCount")
    val likeCount: Int?,
    @SerializedName("MemberRegisterTime")
    val memberRegisterTime: Long?,
    @SerializedName("ViewerEvaluationInfo")
    val viewerEvaluationInfo: ViewerEvaluationInfo?
)