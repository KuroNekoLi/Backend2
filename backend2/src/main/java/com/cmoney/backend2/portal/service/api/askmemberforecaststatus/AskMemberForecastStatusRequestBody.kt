package com.cmoney.backend2.portal.service.api.askmemberforecaststatus
import com.google.gson.annotations.SerializedName


data class AskMemberForecastStatusRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("Guid")
    val guid: String
)