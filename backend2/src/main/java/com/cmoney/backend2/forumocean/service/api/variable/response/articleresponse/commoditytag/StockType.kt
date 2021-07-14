package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag

import com.google.gson.annotations.SerializedName

enum class StockType {
    /**
     * 台股
     *
     */
    @SerializedName("Stock")
    Stock,

    /**
     * 美股
     *
     */
    @SerializedName("USStock")
    USStock,

    /**
     * 興櫃
     *
     */
    @SerializedName("Emerging")
    Emerging
}