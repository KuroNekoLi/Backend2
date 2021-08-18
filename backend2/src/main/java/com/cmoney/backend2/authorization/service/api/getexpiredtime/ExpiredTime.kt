package com.cmoney.backend2.authorization.service.api.getexpiredtime

import com.google.gson.annotations.SerializedName

data class ExpiredTime(
    @SerializedName("expiredTime")
    val expiredTime: Long?,
    @SerializedName("serverTime")
    val serverTime: Long?
)
