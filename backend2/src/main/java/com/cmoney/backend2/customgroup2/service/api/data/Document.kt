package com.cmoney.backend2.customgroup2.service.api.data


import com.google.gson.annotations.SerializedName

/**
 * 自選股文件
 *
 * @property displayName 顯示名稱
 * @property id 文件ID
 * @property marketType 市場類別all, stock, broker , warrant, ustock, bond, brokerbranch
 * @property stocks 股票清單
 * @property type 文件類型，預設填入StockGroup
 * @constructor Create empty Document
 */
data class Document(
    @SerializedName("id", alternate = ["Id"])
    val id: String? = null,
    @SerializedName("displayName", alternate = ["DisplayName"])
    val displayName: String?,
    @SerializedName("marketType", alternate = ["MarketType"])
    val marketType: String?,
    @SerializedName("type", alternate = ["Type"])
    val type: String? = "StockGroup",
    @SerializedName("stocks", alternate = ["Stocks"])
    val stocks: List<String>?
)