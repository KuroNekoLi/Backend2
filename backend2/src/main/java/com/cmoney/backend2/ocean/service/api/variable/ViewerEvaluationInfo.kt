package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class ViewerEvaluationInfo(
    @SerializedName("Content")
    val content: String?,
    @SerializedName("Score")
    val score: Int?
)