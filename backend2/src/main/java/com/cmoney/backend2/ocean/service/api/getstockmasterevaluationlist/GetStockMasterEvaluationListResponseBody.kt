package com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist


import com.google.gson.annotations.SerializedName

data class GetStockMasterEvaluationListResponseBody(
    @SerializedName("MasterEvaluationScores")
    val masterEvaluationScores: List<MasterEvaluationScore?>?
)