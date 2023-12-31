package com.cmoney.backend2.customgroup2.service.api.data


import com.google.gson.annotations.SerializedName

/**
 * 股市標的
 *
 * @property id 編號
 * @property name 顯示名稱
 * @property marketType 市場類別
 * @property type 子類別
 */
data class RawStock(
    @SerializedName("key")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("marketType")
    val marketType: Int?,
    @SerializedName("type")
    val type: Int?
)