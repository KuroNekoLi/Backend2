package com.cmoney.backend2.customgroup2.service.api.searchstocksbymarkettype

import com.google.gson.annotations.SerializedName

data class SearchStocksByMarketTypeRequestBody(
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("marketType")
    val marketTypes: List<String>
)