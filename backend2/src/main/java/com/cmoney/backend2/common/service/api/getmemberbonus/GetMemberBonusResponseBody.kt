package com.cmoney.backend2.common.service.api.getmemberbonus

import com.google.gson.annotations.SerializedName

data class GetMemberBonusResponseBody(
    @SerializedName("Bonus")
    val bonus : Int
)