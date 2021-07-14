package com.cmoney.backend2.forumocean.service.api.group.getmember

import com.cmoney.backend2.forumocean.service.api.variable.response.GroupPositionInfo
import com.google.gson.annotations.SerializedName

data class GroupMember(
    @SerializedName("memberId")
    val memberId : Long?,
    @SerializedName("position")
    val position: GroupPositionInfo?
)