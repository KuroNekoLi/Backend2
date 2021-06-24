package com.cmoney.backend2.ocean.service.api.createannouncement

import com.google.gson.annotations.SerializedName

data class CreateAnnouncementRequest(
    @SerializedName("ArticleId")
    val articleId: Long,
    @SerializedName("IsPinned")
    val isPinned: Boolean,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)
