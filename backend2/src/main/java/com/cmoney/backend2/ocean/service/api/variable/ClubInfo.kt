package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class ClubInfo(
    @SerializedName("IsMessagePublic")
    val isMessagePublic: Boolean?,
    @SerializedName("JoinMethod")
    val joinMethod: ClubJoinMethod?,
    @SerializedName("MemberCount")
    val memberCount: Int?
)