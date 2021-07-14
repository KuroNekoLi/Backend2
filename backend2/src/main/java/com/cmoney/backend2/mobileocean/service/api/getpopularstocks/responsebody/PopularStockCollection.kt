package com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody


import com.google.gson.annotations.SerializedName

data class PopularStockCollection(
    @SerializedName("Stocks")
    val stocks: List<PopularStock?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
)