package com.cmoney.backend2.media.service.api.getpaidmedialistofmemberbyappid

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("PreviewImageUrl")
    val previewImageUrl: String?,
    @SerializedName("StartTime")
    val startTime: Long?,
    @SerializedName("EndTime")
    val endTime: Long?,
    @SerializedName("MediaType")
    val mediaType: Int?,
    @SerializedName("ExpireTime")
    val expireTime: Long?,
    @SerializedName("ReleaseTimeMills")
    val releaseTimeMills: Long?
)
