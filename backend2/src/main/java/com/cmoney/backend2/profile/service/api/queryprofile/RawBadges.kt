package com.cmoney.backend2.profile.service.api.queryprofile

import com.google.gson.annotations.SerializedName

data class RawBadges(
    @SerializedName("badgeId")
    val id : Long?,
    @SerializedName("isEquipped")
    val isEquipped: Boolean?,
    @SerializedName("hasRead")
    val hasRead: Boolean?
)
