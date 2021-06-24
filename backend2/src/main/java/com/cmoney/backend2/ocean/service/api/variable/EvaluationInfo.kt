package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class EvaluationInfo(
    @SerializedName("AverageScore")
    val averageScore: Double?,
    @SerializedName("EvaluationCount")
    val evaluationCount: Int?
)