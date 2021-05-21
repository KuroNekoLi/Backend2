package com.cmoney.backend2.portal.service.api.getactivitynowinfo
import com.google.gson.annotations.SerializedName


data class GetActivityNowInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("CommKey")
    val commKey: String
)