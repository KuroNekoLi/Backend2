package com.cmoney.backend2.mobileocean.service.api.common.article

import com.google.gson.annotations.SerializedName

data class StockInfo(
    @SerializedName("BullBear")
    val bullBear: BullBear?,
    @SerializedName("CommKey")
    val commKey: String?,
    @SerializedName("CommName")
    val commName: String?,
    @SerializedName("CommMarket")
    val commMarket: MarketType?
)