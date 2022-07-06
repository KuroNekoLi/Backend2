package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApprovalDTO(
    @SerializedName("approval")
    val approval: Boolean?,
    @SerializedName("id")
    val id: Int?
)