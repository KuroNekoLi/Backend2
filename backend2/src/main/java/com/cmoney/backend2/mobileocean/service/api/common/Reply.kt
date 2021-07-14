package com.cmoney.backend2.mobileocean.service.api.common


import com.google.gson.annotations.SerializedName

data class Reply(
    @SerializedName("AbleToReadReply")
    val ableToReadReply: Boolean?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("AuthorChannelId")
    val authorChannelId: Long?,
    @SerializedName("AuthorImage")
    val authorImage: String?,
    @SerializedName("AuthorName")
    val authorName: String?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("ContentImage")
    val contentImage: String?,
    @SerializedName("ContentVideoPath")
    val contentVideoPath: String?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("DiamondInfo")
    val diamondInfo: DiamondInfo?,
    @SerializedName("DislikeCount")
    val dislikeCount: Int?,
    @SerializedName("IsDisliked")
    val isDisliked: Boolean?,
    @SerializedName("IsLiked")
    val isLiked: Boolean?,
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("LikeCount")
    val likeCount: Int?
)