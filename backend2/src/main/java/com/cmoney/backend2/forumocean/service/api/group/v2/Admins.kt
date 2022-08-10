package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 管理權限用戶清單
 */
data class Admins(
    /**
     * 管理員 memberId
     */
    @SerializedName("managers")
    val managers: List<Long>?,
    /**
     * 社長 memberId
     */
    @SerializedName("owner")
    val owner: Long?
)