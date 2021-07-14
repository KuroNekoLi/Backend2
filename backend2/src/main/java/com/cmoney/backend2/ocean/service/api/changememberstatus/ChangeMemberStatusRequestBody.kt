package com.cmoney.backend2.ocean.service.api.changememberstatus

import com.google.gson.annotations.SerializedName

data class ChangeMemberStatusRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ClubChannelId")
    val clubChannelId: Long,
    @SerializedName("MemberChannelIds")
    val memberChannelIds: String,
    @SerializedName("Operation")
    val operation: Int
)