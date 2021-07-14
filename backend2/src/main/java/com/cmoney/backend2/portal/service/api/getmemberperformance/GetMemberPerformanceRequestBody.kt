package com.cmoney.backend2.portal.service.api.getmemberperformance
import com.google.gson.annotations.SerializedName


data class GetMemberPerformanceRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("QueryGuid")
    val queryGuid: String
)