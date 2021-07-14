package com.cmoney.backend2.mobileocean.service.api.getforumpopulararticles


import com.google.gson.annotations.SerializedName

/**
 * 股票Tag資訊
 *
 * @property key 股票代號
 * @property name 股票名稱
 */
data class StockInfo(
    @SerializedName("Key")
    val key: String?,
    @SerializedName("Name")
    val name: String?
)