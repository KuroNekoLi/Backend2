package com.cmoney.backend2.profile.service.api.queryprofile


import com.google.gson.annotations.SerializedName

data class RawLevelInfo(
    @SerializedName("exp")
    val exp: Double?,
    @SerializedName("level")
    val level: Long?,
    @SerializedName("levelExp")
    val levelExp: Long?,
    @SerializedName("levelExpToNext")
    val levelExpToNext: Long?
)