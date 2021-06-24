package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

enum class MarketType(val value: String) {
    /**
     * 上市,上櫃
     */
    @SerializedName("Stock")
    Stock("Stock"),
    /**
     * 興櫃
     */
    @SerializedName("Emerging")
    Emerging("Emerging"),
    /**
     * 美股
     */
    @SerializedName("UsStock")
    UsStock("UsStock");
}