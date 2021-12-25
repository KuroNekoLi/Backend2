package com.cmoney.backend2.profile.service.api.queryprofile


import com.google.gson.annotations.SerializedName

data class LevelInfo(
    @SerializedName("exp")
    val exp: Double?,
    @SerializedName("level")
    val level: Int?,
    @SerializedName("levelExp")
    val levelExp: Int?,
    @SerializedName("levelExpToNext")
    val levelExpToNext: Int?
)