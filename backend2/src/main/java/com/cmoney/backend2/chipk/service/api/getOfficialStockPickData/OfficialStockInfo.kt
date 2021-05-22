package com.cmoney.backend2.chipk.service.api.getOfficialStockPickData

import com.google.gson.annotations.SerializedName

data class OfficialStockInfo(
    @SerializedName("ReplaceSymbol")
    val replaceSymbol: String = "",

    @SerializedName("Description")
    val description: String = "",

    @SerializedName("StockList")
    val stockList: List<OfficialStock> = emptyList()
)
