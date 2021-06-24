package com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount

import com.google.gson.annotations.SerializedName

data class AskStockTendencyAmountResponse(
    @SerializedName("Amount")
    val amount: Int?
)