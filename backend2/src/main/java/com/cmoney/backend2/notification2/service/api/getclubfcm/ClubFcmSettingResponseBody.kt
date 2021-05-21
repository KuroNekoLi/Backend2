package com.cmoney.backend2.notification2.service.api.getclubfcm
import com.google.gson.annotations.SerializedName

data class ClubFcmSettingResponseBody(
    @SerializedName("isSelected")
    val isSelected: Boolean?,
    @SerializedName("settingName")
    val settingName: String?,
    @SerializedName("pushSettingType")
    val pushSettingType: Int?
)