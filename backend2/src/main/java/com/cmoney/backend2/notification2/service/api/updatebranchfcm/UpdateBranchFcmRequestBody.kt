package com.cmoney.backend2.notification2.service.api.updatebranchfcm

import com.google.gson.annotations.SerializedName

data class UpdateBranchFcmRequestBody(
    @SerializedName("IsNeedPush")
    val isNeedPush: Boolean,
    @SerializedName("PushSettingId")
    val pushSettingId: Int?,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)