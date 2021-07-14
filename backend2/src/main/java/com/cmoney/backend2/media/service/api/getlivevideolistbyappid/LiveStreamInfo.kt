package com.cmoney.backend2.media.service.api.getlivevideolistbyappid


import com.cmoney.backend2.media.service.api.getmedialistbyappid.Tag
import com.google.gson.annotations.SerializedName

data class LiveStreamInfo(
    @SerializedName("AuthorName")
    val authorName: String?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("EndTime")
    val endTime: Long?,
    @SerializedName("HasAuth")
    val hasAuth: Boolean?,
    @SerializedName("Id")
    val id: Long?,
    @SerializedName("IsVIP")
    val isVIP: Boolean?,
    @SerializedName("OnlineCount")
    val onlineCount: Int?,
    @SerializedName("PreviewImageUrl")
    val previewImageUrl: String?,
    @SerializedName("Price")
    val price: Double?,
    @SerializedName("RoomDescription")
    val roomDescription: String?,
    @SerializedName("StartTime")
    val startTime: Long?,
    @SerializedName("Subtitle")
    val subtitle: String?,
    @SerializedName("TagList")
    val tagList: List<Tag?>?,
    @SerializedName("Title")
    val title: String?
)