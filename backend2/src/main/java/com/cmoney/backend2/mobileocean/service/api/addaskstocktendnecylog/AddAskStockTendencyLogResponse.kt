package com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog

import com.google.gson.annotations.SerializedName

data class AddAskStockTendencyLogResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)