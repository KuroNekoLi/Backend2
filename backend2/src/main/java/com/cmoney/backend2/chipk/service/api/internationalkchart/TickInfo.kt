package com.cmoney.backend2.chipk.service.api.internationalkchart


import com.google.gson.annotations.SerializedName

data class TickInfo(
    @SerializedName("CeilingPrice")
    val ceilingPrice: String?,
    @SerializedName("ClosePrice")
    val closePrice: String?,
    @SerializedName("Date")
    val date: String?,
    @SerializedName("LowestPrice")
    val lowestPrice: String?,
    @SerializedName("OpenPrice")
    val openPrice: String?,
    @SerializedName("PriceChange")
    val priceChange: String?,
    @SerializedName("QuoteChange")
    val quoteChange: String?,
    @SerializedName("TotalQty")
    val totalQty: String?
)