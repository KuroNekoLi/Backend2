package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class LevelInfo(
    @SerializedName("CurrentExperience")
    val currentExperience: Int?,
    @SerializedName("CurrentLevel")
    val currentLevel: Int?,
    @SerializedName("LevelExpThreshold")
    val levelExpThreshold: Int?,
    @SerializedName("Next")
    val next: Int?
)