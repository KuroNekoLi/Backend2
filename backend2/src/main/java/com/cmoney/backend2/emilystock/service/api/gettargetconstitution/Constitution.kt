package com.cmoney.backend2.emilystock.service.api.gettargetconstitution

import com.google.gson.annotations.SerializedName

data class Constitution(
    @SerializedName("CommKey")
    val commKey: String?,

    @SerializedName("CommName")
    val commName: String?,

    @SerializedName("MarginOfSafetyDiscount")
    val marginOfSafetyDiscount: String?,

    @SerializedName("AvgEmilyPriceGroup")
    val avgEmilyPriceGroup: EmilyPriceGroup?,

    @SerializedName("EmilyPriceGroups")
    val emilyPriceGroups: List<EmilyPriceGroup?>?,

    @SerializedName("DataDate")
    val dataDate: String?,

    @SerializedName("ReturnOnPriceDifference")
    val returnOnPriceDifference: String?,

    @SerializedName("Yields")
    val yields: String?,

    @SerializedName("YieldsNear10Year")
    val yieldsNear10Year: String?,

    @SerializedName("SubIndustry")
    val subIndustry: String?,

    @SerializedName("StockScaleType")
    val stockScaleType: Int?,

    @SerializedName("CapitalStock")
    val CapitalStock: String?,

    @SerializedName("MarketType")
    val marketType: Int?,

    @SerializedName("PublicYear")
    val publicYear: String?,

    @SerializedName("PER")
    val per: String?,

    @SerializedName("PBR")
    val pbr: String?,

    @SerializedName("TaxDeductible")
    val taxDeductible: String?,

    @SerializedName("EPSNearSeason")
    val epsNearSeason: String?,

    @SerializedName("EPSNearYear")
    val epsNearYear: String?,

    @SerializedName("StockClassifyType")
    val stockClassifyType: Int?,

    @SerializedName("Near10YearSurplusAllotment")
    val near10YearSurplusAllotment: String?,

    @SerializedName("EmilyCaseScores")
    val emilyCaseScores: List<EmilyCaseScore?>?
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

    data class EmilyCaseScore(
        @SerializedName("CaseScoreType")
        val caseScoreType: Int?,

        @SerializedName("CaseScoreGroupType")
        val caseScoreGroupType: Int?,

        @SerializedName("CaseValue")
        val caseValue: String?,

        @SerializedName("IsGood")
        val IsGood: Boolean?
    )
}