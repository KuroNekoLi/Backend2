package com.cmoney.backend2.notification2.service.api.getbranchfcm
import com.google.gson.annotations.SerializedName

data class BranchSettingRequestBody(
    @SerializedName("isNeedPush")
    val isNeedPush: Boolean?,
    @SerializedName("pushSettingId")
    val pushSettingId: Int?,
    @SerializedName("settingName")
    val settingName: String?
)