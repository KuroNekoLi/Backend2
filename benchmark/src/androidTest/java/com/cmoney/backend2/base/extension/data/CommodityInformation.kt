package com.cmoney.backend2.base.extension.data

import com.google.gson.annotations.SerializedName

data class CommodityInformation(
    @SerializedName("股票代號")
    val id: String?,
    @SerializedName("股票名稱")
    val name: String?,
    @SerializedName("日期")
    val date: String?,
    @SerializedName("股票名稱1")
    val name1: String?,
    @SerializedName("產業指數代號")
    val code: String?,
    @SerializedName("上市上櫃")
    val type: String?
)
