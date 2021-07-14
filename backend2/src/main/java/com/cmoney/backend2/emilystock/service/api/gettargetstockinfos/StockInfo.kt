package com.cmoney.backend2.emilystock.service.api.gettargetstockinfos

import com.google.gson.annotations.SerializedName

data class StockInfo(
    @SerializedName("CommKey")
    val commKey: String?,

    @SerializedName("CommName")
    val commName: String?,

    @SerializedName("StockClassifyType")
    val stockClassifyType: Int?,

    @SerializedName("Yields")
    val yields: String?,

    @SerializedName("MarginOfSafetyDiscount")
    val marginOfSafetyDiscount: String?,

    @SerializedName("EmilyPriceGroups")
    val emilyPriceGroups: List<EmilyPriceGroup?>?
) {

    data class EmilyPriceGroup(

        @SerializedName("PriceCheap")
        val priceCheap: String?,

        @SerializedName("PriceNormal")
        val priceNormal: String?,

        @SerializedName("PriceExpensive")
        val priceExpensive: String?,

        @SerializedName("PriceCaculateType")
        val priceCaculateType: Int?,

        @SerializedName("IsDefault")
        val isDefault: Boolean?
    )
}