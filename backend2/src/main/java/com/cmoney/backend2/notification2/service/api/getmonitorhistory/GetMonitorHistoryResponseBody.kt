package com.cmoney.backend2.notification2.service.api.getmonitorhistory

import com.google.gson.annotations.SerializedName

data class GetMonitorHistoryResponseBody(
    @SerializedName("sn"  , alternate = ["Sn"])
    val sn : Long?,
    @SerializedName("body" , alternate = ["Body"])
    val body : String?,
    @SerializedName("parameter" , alternate = ["Parameter"])
    val parameter : MonitorHistoryParameter?,
    @SerializedName("createTime" , alternate = ["CreateTime"])
    val createTime : Long?
) {
    data class MonitorHistoryParameter (
        @SerializedName("CommKey" , alternate = ["commKey"])
        val commKey : String?,
        @SerializedName("CommName" , alternate = ["commName"])
        val commName : String?
    )
}