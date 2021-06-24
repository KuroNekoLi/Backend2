package com.cmoney.backend2.ocean.service.api.invite


import com.google.gson.annotations.SerializedName

data class InviteRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ClubChannelId")
    val clubChannelId: Long,
    @SerializedName("MemberChannelIds")
    val memberChannelIds: String
)