package com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist


import com.google.gson.annotations.SerializedName

data class GetStockMasterEvaluationListRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("StockIds")
    val stockIds: List<String>
)