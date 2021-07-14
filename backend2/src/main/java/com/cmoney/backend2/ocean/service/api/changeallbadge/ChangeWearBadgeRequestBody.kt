package com.cmoney.backend2.ocean.service.api.changeallbadge

import com.google.gson.annotations.SerializedName

data class ChangeWearBadgeRequestBody(
    @SerializedName("BadgeIds")
    val badgeIds : List<Int>,
    @SerializedName("Wear")
    val  wear : Boolean,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)