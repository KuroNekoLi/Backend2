package com.cmoney.backend2.forumocean.service.api.relationship.getdonate

import com.google.gson.annotations.SerializedName

data class DonateInfo(
    @SerializedName("memberId")
    val memberId: Long?,
    @SerializedName("donateValue")
    val donateValue: Int?
)
