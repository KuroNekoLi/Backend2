package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("ArticleType")
    val articleType: OceanArticleType?,
    @SerializedName("AuthorInfo")
    val authorInfo: AuthorInfo?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("ContentImage")
    val contentImage: String?,
    @SerializedName("ContentVideoUrl")
    val contentVideoUrl: String?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("DislikeCount")
    val dislikeCount: Int?,
    @SerializedName("HasAuthToReadReply")
    val hasAuthToReadReply: Boolean?,
    @SerializedName("IsDisLike")
    val isDisLike: Boolean?,
    @SerializedName("IsLike")
    val isLike: Boolean?,
    @SerializedName("LikeCount")
    val likeCount: Int?
)