package com.cmoney.backend2.ocean.service.api.getbadgescollection

import com.google.gson.annotations.SerializedName

data class GetBadgesCollection(
    @SerializedName("BadgeId")
    val badgeId: Int?,
    @SerializedName("Collectioned")
    val collectioned: Boolean?,
    @SerializedName("Read")
    val read: Boolean?,
    @SerializedName("Wear")
    val wear: Boolean?
)