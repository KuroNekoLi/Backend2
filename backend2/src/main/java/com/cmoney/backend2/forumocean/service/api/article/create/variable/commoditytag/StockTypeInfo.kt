package com.cmoney.backend2.forumocean.service.api.article.create.variable.commoditytag

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
