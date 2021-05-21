package com.cmoney.backend2.emilystock.service.api.getfiltercondition

import com.google.gson.annotations.SerializedName

data class GetFilterConditionResponse(
    @SerializedName("Response")
    val response: List<FilterCondition?>?
) {

    data class FilterCondition(
        @SerializedName("CommKey")
        val commKey: String?,

        @SerializedName("Qualified")
        val qualified: Boolean?,

        @SerializedName("YieldRate")
        val yieldRate: Boolean?,

        @SerializedName("EarnMoneyYr")
        val earnMoneyYr: Boolean?,

        @SerializedName("CashDividendYr")
        val cashDividendYr: Boolean?,

        @SerializedName("ListingAndOTC10Yr")
        val listingAndOTC10Yr: Boolean?,

        @SerializedName("MediumLargeStocks")
        val mediumLargeStocks: Boolean?
    )
}