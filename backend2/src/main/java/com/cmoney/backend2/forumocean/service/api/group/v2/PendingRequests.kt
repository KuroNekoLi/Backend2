package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 待審核api
 */
data class PendingRequests(
    @SerializedName("lastTimestamp")
    val lastTimestamp: Long?,
    @SerializedName("pendingList")
    val pendingList: List<PendingRequest>?
)