package com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo
import com.google.gson.annotations.SerializedName


data class AskAllMemberLastForecastInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)