package com.cmoney.backend2.portal.service.api.getranking
import com.google.gson.annotations.SerializedName


data class GetRankingRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("SkipCount")
    val skipCount: Int
)