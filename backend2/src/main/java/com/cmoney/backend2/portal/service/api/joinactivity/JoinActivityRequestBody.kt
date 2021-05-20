package com.cmoney.backend2.portal.service.api.joinactivity
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.google.gson.annotations.SerializedName


data class JoinActivityRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("ForecastValue")
    val forecastValue: ForecastValue,
    @SerializedName("Guid")
    val guid: String
)