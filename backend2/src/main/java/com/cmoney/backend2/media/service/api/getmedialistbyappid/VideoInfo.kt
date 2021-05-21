package com.cmoney.backend2.media.service.api.getmedialistbyappid


import com.google.gson.annotations.SerializedName

data class VideoInfo(
    @SerializedName("AuthorName")
    val authorName: String?,
    @SerializedName("ExpireTime")
    val expireTime: Long?,
    @SerializedName("HasAuth")
    val hasAuth: Boolean?,
    @SerializedName("Id")
    val id: Long?,
    @SerializedName("IsVIP")
    val isVIP: Boolean?,
    @SerializedName("PreviewImageUrl")
    val previewImageUrl: String?,
    @SerializedName("Price")
    val price: Double?,
    @SerializedName("ReleaseTimeMills")
    val releaseTimeMills: Long?,
    @SerializedName("StartTime")
    val startTime: Long?,
    @SerializedName("Subtitle")
    val subtitle: String?,
    @SerializedName("TagList")
    val tagList: List<Tag?>?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("ViewCount")
    val viewCount: Int?
)