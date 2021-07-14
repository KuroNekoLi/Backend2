package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

data class StockTag(
    @SerializedName("BullBear")
    val bullBear: BullBear?,
    @SerializedName("CommKey")
    val commKey: String?,
    @SerializedName("CommName")
    val commName: String?
)