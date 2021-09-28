package com.cmoney.backend2.customgroup2.service.api.searchstocksbymarkettype

import com.cmoney.backend2.customgroup2.service.api.data.RequestMarketType
import com.google.gson.annotations.SerializedName

data class SearchStocksByMarketTypeRequestBody(
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("commodityTypes")
    val marketTypes: List<RequestMarketType>
)