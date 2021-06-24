package com.cmoney.backend2.ocean.service.api.getstockmasterevaluation

import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("DisplayName")
    val displayName: String?,
    @SerializedName("IsSatisfy")
    val isSatisfy: Boolean?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("ResultValue")
    val resultValue: Double?
)