package com.cmoney.backend2.ocean.service.api.getstockmasterevaluation

import com.google.gson.annotations.SerializedName

data class Trend(
    @SerializedName("Detail")
    val detail: List<Detail?>?,
    @SerializedName("DisplayName")
    val displayName: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Score")
    val score: Int?
)