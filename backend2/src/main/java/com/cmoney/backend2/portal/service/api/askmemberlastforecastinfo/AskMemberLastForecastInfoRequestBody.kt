package com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo
import com.google.gson.annotations.SerializedName


data class AskMemberLastForecastInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("Guid")
    val guid: String
)