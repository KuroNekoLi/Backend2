package com.cmoney.backend2.chipk.service.api.getOfficialStockPickData

import com.google.gson.annotations.SerializedName

data class OfficialStock(
    @SerializedName("StockId")
    val stockId: String = "",
    @SerializedName("StockName")
    val stockName: String = "",
    @SerializedName("Info")
    val info: String = ""
)
