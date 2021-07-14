package com.cmoney.backend2.ocean.service.api.searchchannel
import com.cmoney.backend2.ocean.service.api.variable.ChannelTypes
import com.google.gson.annotations.SerializedName


data class SearchChannelRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ChannelTypes")
    val channelTypes: ChannelTypes,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Keyword")
    val keyword: String
)