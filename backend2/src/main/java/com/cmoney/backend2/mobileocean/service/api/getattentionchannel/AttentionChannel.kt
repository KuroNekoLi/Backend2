package com.cmoney.backend2.mobileocean.service.api.getattentionchannel


import com.cmoney.backend2.mobileocean.service.api.common.DiamondInfo
import com.cmoney.backend2.mobileocean.service.api.common.LevelInfo
import com.google.gson.annotations.SerializedName

data class AttentionChannel(
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
    @SerializedName("FansCount")
    val fansCount: Int?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("IsFollowed")
    val isFollowed: Boolean?,
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("LikeCount")
    val likeCount: Int?
)