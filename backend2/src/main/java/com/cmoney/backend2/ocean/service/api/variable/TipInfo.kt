package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class TipInfo(
    @SerializedName("MemberTip")
    val memberTip: Int?,
    @SerializedName("TotalTip")
    val totalTip: Int?
)