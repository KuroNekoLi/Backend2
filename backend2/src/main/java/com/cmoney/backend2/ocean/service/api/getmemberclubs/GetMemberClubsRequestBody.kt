package com.cmoney.backend2.ocean.service.api.getmemberclubs


import com.google.gson.annotations.SerializedName

data class GetMemberClubsRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("MemberChannelId")
    val memberChannelId: Long,
    @SerializedName("NeedInfo")
    val needInfo: Int,
    @SerializedName("Relation")
    val relation: Int
)