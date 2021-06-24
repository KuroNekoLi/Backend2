package com.cmoney.backend2.ocean.service.api.getbadgeandrequirement

import com.cmoney.backend2.ocean.service.api.variable.Badge
import com.cmoney.backend2.ocean.service.api.variable.Requirement
import com.google.gson.annotations.SerializedName

data class GetBadgeAndRequirementResponse(
    @SerializedName("Badge")
    val badge: Badge?,
    @SerializedName("BadgeId")
    val badgeId: Int?,
    @SerializedName("Requirement")
    val requirement: Requirement?
)