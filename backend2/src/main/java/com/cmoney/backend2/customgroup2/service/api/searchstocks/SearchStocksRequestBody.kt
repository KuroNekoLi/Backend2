package com.cmoney.backend2.customgroup2.service.api.searchstocks

import com.google.gson.annotations.SerializedName

data class SearchStocksRequestBody(
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("language")
    val language: String
)