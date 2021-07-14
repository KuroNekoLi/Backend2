package com.cmoney.backend2.ocean.service.api.checkHasJoinedClub

import com.google.gson.annotations.SerializedName

data class HasJoinedClubRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("MemberChannelId")
    val channelId: Long,
    @SerializedName("Relation")
    val relation: Int
)