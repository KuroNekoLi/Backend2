package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 核准
 */
data class Approval(
    /**
     * 核准加入
     */
    @SerializedName("approval")
    val approval: Boolean?,
    /**
     * 用戶memberId
     */
    @SerializedName("id")
    val id: Long?
)