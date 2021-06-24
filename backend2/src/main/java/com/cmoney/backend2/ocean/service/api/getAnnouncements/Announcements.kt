package com.cmoney.backend2.ocean.service.api.getAnnouncements

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

data class Announcements(
    @SerializedName("Article")
    val article: Article,
    @SerializedName("IsPinned")
    val isPinned: Boolean,
    @SerializedName("PinnedTime")
    val pinnedTime: Long,
    @SerializedName("CreateTime")
    val createTime: Long
)