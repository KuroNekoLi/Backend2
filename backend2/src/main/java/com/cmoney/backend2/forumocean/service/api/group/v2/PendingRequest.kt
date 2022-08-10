package com.cmoney.backend2.forumocean.service.api.group.v2


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * 待審核
 */
data class PendingRequest(
    /**
     * 建立時間
     */
    @SerializedName("createTime")
    val createTime: Long?,
    /**
     * 用戶id
     */
    @SerializedName("id")
    val id: Long?,
    /**
     * 理由
     */
    @SerializedName("reason")
    val reason: String?
)