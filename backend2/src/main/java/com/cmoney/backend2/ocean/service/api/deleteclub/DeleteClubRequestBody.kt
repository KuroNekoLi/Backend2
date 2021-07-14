package com.cmoney.backend2.ocean.service.api.deleteclub


import com.google.gson.annotations.SerializedName

data class DeleteClubRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ClubChannelId")
    val clubChannelId: Long
)