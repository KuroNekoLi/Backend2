package com.cmoney.backend2.ocean.service.api.updateclubdescription

import com.google.gson.annotations.SerializedName

data class UpdateClubDescriptionRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("ClubChannelId")
    val clubChannelId: Long,
    @SerializedName("Description")
    val description: String
)