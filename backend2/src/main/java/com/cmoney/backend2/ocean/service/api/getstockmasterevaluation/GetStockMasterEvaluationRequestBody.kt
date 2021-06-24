package com.cmoney.backend2.ocean.service.api.getstockmasterevaluation

import com.google.gson.annotations.SerializedName

data class GetStockMasterEvaluationRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("StockId")
    val stockId: String
)