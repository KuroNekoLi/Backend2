package com.cmoney.backend2.ocean.service.api.getunreadbadges

import com.google.gson.annotations.SerializedName

data class GetUnreadBadgesRequestBody(
    @SerializedName("Guid")
    val guid : String,

    @SerializedName("AppId")
    val appId: Int
)