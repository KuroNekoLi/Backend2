package com.cmoney.backend2.customgroup.service.api.searchstocks

import com.google.gson.annotations.SerializedName

data class SearchStocksRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("keyword")
    val keyword: String
)