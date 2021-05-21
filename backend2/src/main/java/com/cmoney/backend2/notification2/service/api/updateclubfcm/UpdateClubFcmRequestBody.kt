package com.cmoney.backend2.notification2.service.api.updateclubfcm

import com.google.gson.annotations.SerializedName

data class UpdateClubFcmRequestBody(
    @SerializedName("PushSettingType")
    val pushSettingType : Int,
    @SerializedName("ClubId")
    val clubId : Long,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)