package com.cmoney.backend2.notification2.service

import com.google.gson.annotations.SerializedName

data class TestParameter26(
    @SerializedName("CommKey", alternate = ["commKey"])
    val commonKey: String?,
    @SerializedName("ClosingPrice", alternate = ["closingPrice"])
    val closingPrice: Double?,
    @SerializedName("ChangeRate", alternate = ["changeRate"])
    val changeRate: Double?,
    @SerializedName("ChangeAmount", alternate = ["changeAmount"])
    val changeAmount: Double?,
    @SerializedName("ValueRangeType", alternate = ["valueRangeType"])
    val valueRangeType: String?,
    @SerializedName("EvaluationType", alternate = ["evaluationType"])
    val evaluationType: String?
)
