package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class MemberClubInfo(
    @SerializedName("LastViewTime")
    val lastViewTime: Long?,
    @SerializedName("MemberPosition")
    val memberPosition: MemberPosition?
)