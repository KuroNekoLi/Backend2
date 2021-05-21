package com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord

import com.google.gson.annotations.SerializedName

data class TrafficLightRecord(
    @SerializedName("CommKey")
    val commKey: String?,

    @SerializedName("Name")
    val name: String?,

    @SerializedName("SalePrice")
    val salePrice: Double?,

    @SerializedName("PriceChange")
    val priceChange: Double?,

    @SerializedName("QuoteChange")
    val quoteChange: Double?,

    @SerializedName("TrafficLightType")
    val trafficLightType: Int?,

    @SerializedName("CreateTime")
    val createTime: Int?,

    @SerializedName("TrafficLightText")
    val trafficLightText: String?
)