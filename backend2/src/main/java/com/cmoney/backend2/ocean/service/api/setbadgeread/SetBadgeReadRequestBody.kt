package com.cmoney.backend2.ocean.service.api.setbadgeread

import com.google.gson.annotations.SerializedName

data class SetBadgeReadRequestBody(
    @SerializedName("BadgeId")
    val badgeId : Long,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)