package com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo
import com.google.gson.annotations.SerializedName


data class GetActivitiesBaseInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int
)