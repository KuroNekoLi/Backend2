package com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist


import com.google.gson.annotations.SerializedName

data class ProductInfo(
    @SerializedName("Commkey")
    val commkey: String?,
    @SerializedName("CountryCode")
    val countryCode: Int?,
    @SerializedName("IsShowPreviousClosePr")
    val isShowPreviousClosePr: Boolean?,
    @SerializedName("Name")
    val name: String?
)