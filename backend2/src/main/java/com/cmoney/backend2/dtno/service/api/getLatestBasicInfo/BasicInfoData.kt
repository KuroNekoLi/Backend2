package com.cmoney.backend2.dtno.service.api.getLatestBasicInfo

import com.google.gson.annotations.SerializedName

data class BasicInfoData(

    @SerializedName("Commkey")
    val commKey: String?,

    @SerializedName("PreClosePrice")
    val preClosePrice: String?,

    @SerializedName("QuoteChange")
    val quoteChange: String?,

    @SerializedName("PriceChange")
    val priceChange: String?,

    @SerializedName("Date")
    val date: String?
)