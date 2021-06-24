package com.cmoney.backend2.ocean.service.api.joinclub

import com.cmoney.backend2.ocean.service.api.variable.MemberPosition
import com.google.gson.annotations.SerializedName

data class JoinClubResponseBody(
    @SerializedName("MemberPosition")
    val memberPosition : MemberPosition?
)