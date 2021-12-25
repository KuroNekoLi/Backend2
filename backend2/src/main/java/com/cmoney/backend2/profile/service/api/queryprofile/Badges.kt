package com.cmoney.backend2.profile.service.api.queryprofile

import com.google.gson.annotations.SerializedName

data class Badges(
    @SerializedName("badgeId")
    val id : Int?,
    @SerializedName("isEquipped")
    val isEquipped: Boolean?,
    @SerializedName("hasRead")
    val hasRead: Boolean?
)
