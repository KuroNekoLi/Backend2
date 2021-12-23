package com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata

import com.google.gson.annotations.SerializedName

/**
 * 股票類型
 */
enum class TradeType {

    /**
     * 現股
     */
    @SerializedName("0")
    Spot,

    /**
     * 融資
     */
    @SerializedName("1")
    MarginTrading,

    /**
     * 融券
     */
    @SerializedName("2")
    ShortSelling,

    /**
     * 興櫃
     */
    @SerializedName("3")
    Emerging,

}
