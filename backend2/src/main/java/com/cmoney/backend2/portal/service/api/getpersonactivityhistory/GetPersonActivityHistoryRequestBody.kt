package com.cmoney.backend2.portal.service.api.getpersonactivityhistory
import com.google.gson.annotations.SerializedName


data class GetPersonActivityHistoryRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("QueryGuid")
    val queryGuid: String,
    @SerializedName("SkipCount")
    val skipCount: Int
)