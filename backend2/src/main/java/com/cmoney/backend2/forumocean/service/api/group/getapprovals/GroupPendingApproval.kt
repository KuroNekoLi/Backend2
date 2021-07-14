package com.cmoney.backend2.forumocean.service.api.group.getapprovals

import com.google.gson.annotations.SerializedName

data class GroupPendingApproval(
    @SerializedName("memberId")
    val memberId : Long?,
    @SerializedName("createTimestamp")
    val createTimestamp : Long?,
    @SerializedName("reason")
    val reason : String?
)