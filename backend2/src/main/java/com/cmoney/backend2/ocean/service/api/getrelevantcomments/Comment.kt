package com.cmoney.backend2.ocean.service.api.getrelevantcomments

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("Id")
    val id: Long?,
    @SerializedName("MemberChannelID")
    val memberChannelId: Long?,
    @SerializedName("LocationChannelID")
    val locationChannelId: Long?,
    @SerializedName("HeadArticleID")
    val headArticleId: Long?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("CountLike")
    val countLike: Int?,
    @SerializedName("VideoName")
    val videoName: String?,
    @SerializedName("VideoUrl")
    val videoUrl: String?,
    @SerializedName("ImageName")
    val imageName: String?,
    @SerializedName("ImageUrl")
    val imageUrl: String?,
    @SerializedName("IsHide")
    val isHide: Boolean?,
    @SerializedName("ArticleType")
    val articleType: Int?,
    @SerializedName("CTime")
    val cTime: Int?,
    @SerializedName("MTime")
    val mTime: Int?
)
