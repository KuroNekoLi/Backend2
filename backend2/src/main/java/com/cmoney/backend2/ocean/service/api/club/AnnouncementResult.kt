package com.cmoney.backend2.ocean.service.api.club

import com.cmoney.backend2.ocean.service.api.variable.Article
import com.google.gson.annotations.SerializedName

/**
 * 公告列表中的物件
 * @property article Article?
 * @property isPinned Boolean?
 * @property pinnedTime Long?
 * @property createTime Long?
 * @constructor
 */
data class AnnouncementResult(
    @SerializedName("Article")
    val article : Article?,

    @SerializedName("IsPinned")
    val isPinned : Boolean?,

    @SerializedName("PinnedTime")
    val pinnedTime : Long?,

    @SerializedName("CreateTime")
    val createTime : Long?
)

