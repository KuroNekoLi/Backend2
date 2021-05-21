package com.cmoney.backend2.notification2.service.api.updatemonitorpushnotification

import com.google.gson.annotations.SerializedName

data class UpdateMonitorPushNotificationRequestBody(
    @SerializedName("ConditionId")
    val conditionId : Long,
    @SerializedName("IsNeedPush")
    val isNeedToPush : Boolean,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId : Int
)