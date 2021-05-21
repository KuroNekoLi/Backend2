package com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody

import com.google.gson.annotations.SerializedName

data class PushSetting(
    @SerializedName("IsNeedPush")
    val isNeedPush: Boolean,
    @SerializedName("PushSettingId")
    val pushSettingId: Int
)
