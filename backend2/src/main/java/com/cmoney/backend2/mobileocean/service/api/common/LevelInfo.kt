package com.cmoney.backend2.mobileocean.service.api.common


import com.google.gson.annotations.SerializedName

/**
 * 等級資料
 *
 * @property currentExperience 目前經驗值
 * @property currentLevel 目前等級
 * @property levelExpThreshold 前次跨等級的經驗值閾值
 * @property next 還需要多少經驗值到下一等級
 */
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