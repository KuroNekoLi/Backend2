package com.cmoney.backend2.ocean.service.api.removeannouncement

import com.google.gson.annotations.SerializedName

data class RemoveAnnouncementRequest(
    @SerializedName("ArticleId")
    val articleId: Long,
    @SerializedName("IsPinned")
    val isPinned: Boolean,
    @SerializedName("AppId")
    val appId: Long,
    @SerializedName("Guid")
    val guid: String
)