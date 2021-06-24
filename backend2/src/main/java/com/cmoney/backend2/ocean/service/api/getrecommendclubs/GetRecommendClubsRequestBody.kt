package com.cmoney.backend2.ocean.service.api.getrecommendclubs


import com.google.gson.annotations.SerializedName

data class GetRecommendClubsRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("SkipCount")
    val skipCount: Int,
    @SerializedName("NeedInfo")
    val needInfo: Int
)