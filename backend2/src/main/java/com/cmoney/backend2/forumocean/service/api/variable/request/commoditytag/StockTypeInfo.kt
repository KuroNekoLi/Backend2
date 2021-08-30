package com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag

import com.google.gson.annotations.SerializedName

enum class StockTypeInfo{
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
