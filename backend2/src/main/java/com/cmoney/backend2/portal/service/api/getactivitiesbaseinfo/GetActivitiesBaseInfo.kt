package com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo
import com.google.gson.annotations.SerializedName


data class GetActivitiesBaseInfo(
    @SerializedName("ActivityBaseInfoList")
    val activityBaseInfoList: List<ActivityBaseInfo>?
)

data class ActivityBaseInfo(
    @SerializedName("Bets")
    val bets: Int?,
    @SerializedName("CommKey")
    val commKey: String?
)