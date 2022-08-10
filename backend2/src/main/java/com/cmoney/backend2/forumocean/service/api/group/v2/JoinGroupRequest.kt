package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 加入社團請求
 */
data class JoinGroupRequest(
    /**
     * 加入原因
     */
    @SerializedName("reason")
    val reason: String?
)