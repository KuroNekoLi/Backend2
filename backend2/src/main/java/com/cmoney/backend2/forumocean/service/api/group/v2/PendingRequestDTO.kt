package com.cmoney.backend2.forumocean.service.api.group.v2


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PendingRequestDTO(
    @SerializedName("createTime")
    val createTime: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("reason")
    val reason: String?
)