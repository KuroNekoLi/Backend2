package com.cmoney.backend2.notification2.service.api.deletemonitor

import com.google.gson.annotations.SerializedName

data class DeleteMonitorRequestBody(
    @SerializedName("ConditionId")
    val conditionId : Long,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId : Int
)