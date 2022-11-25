package com.cmoney.backend2.forumocean.service.api.group.v2

import com.google.gson.annotations.SerializedName

/**
 * 看板未讀資訊
 */
data class BoardReadInfo(
    /**
     * 未讀數
     */
    @SerializedName("unreadCount")
    val unreadCount: Boolean?,
    /**
     * 取下篇文章權重
     */
    @SerializedName("nextStartWeight")
    val nextStartWeight: Int?
)
