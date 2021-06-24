package com.cmoney.backend2.ocean.service.api.getevaluationlist


import com.google.gson.annotations.SerializedName

data class GetEvaluationListResponseBody(
    @SerializedName("Evaluations")
    val evaluations: List<EvaluationRecord>?
)