package com.cmoney.backend2.portal.service.api.getadditionalinfo

import com.google.gson.annotations.SerializedName

data class GetAdditionRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("SettingId")
    val settingId: Int
)