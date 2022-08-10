package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 可用的boardId
 */
data class AvailableBoardIds(
    /**
     * memberIds
     */
    @SerializedName("ids")
    val ids: List<Long>?
)
