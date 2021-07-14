package com.cmoney.backend2.ocean.service.api.leaveclub


import com.google.gson.annotations.SerializedName

data class LeaveClubRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ClubChannelId")
    val clubChannelId: Long
)