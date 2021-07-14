package com.cmoney.backend2.ocean.service.api.getmemberstatuslist


import com.google.gson.annotations.SerializedName

data class GetMemberStatusListRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ChannelId")
    val channelId: Long,
    @SerializedName("Status")
    val status: Int,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("SkipCount")
    val skipCount: Int,
    @SerializedName("NeedInfo")
    val needInfo: Int
)