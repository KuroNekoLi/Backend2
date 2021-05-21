package com.cmoney.backend2.notification2.service.api.updatemainfcm

import com.google.gson.annotations.SerializedName

data class UpdateMainFcmRequestBody(
    @SerializedName("IsNeedPush")
    val isNeedPush: Boolean,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)