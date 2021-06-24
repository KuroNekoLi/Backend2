package com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub
import com.google.gson.annotations.SerializedName


data class GetFansListExcludeJoinedClubRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ExcludeClubChannelId")
    val excludeClubChannelId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("NeedInfo")
    val needInfo: Int,
    @SerializedName("SkipCount")
    val skipCount: Int
)