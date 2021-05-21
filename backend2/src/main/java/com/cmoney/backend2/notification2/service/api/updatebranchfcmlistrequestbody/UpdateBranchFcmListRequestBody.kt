package com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody

import com.google.gson.annotations.SerializedName

data class UpdateBranchFcmListRequestBody(
    @SerializedName("PushSettings")
    val pushSettings: List<PushSetting>,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)
