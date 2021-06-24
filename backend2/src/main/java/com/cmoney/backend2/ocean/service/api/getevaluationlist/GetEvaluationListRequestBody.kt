package com.cmoney.backend2.ocean.service.api.getevaluationlist


import com.google.gson.annotations.SerializedName

data class GetEvaluationListRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ChannelId")
    val channelId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("SkipCount")
    val skipCount: Int,
    @SerializedName("SortType")
    val sortType: Int
)