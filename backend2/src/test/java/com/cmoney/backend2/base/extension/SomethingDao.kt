package com.cmoney.backend2.base.extension

import com.google.gson.annotations.SerializedName

data class SomethingDao(
    @SerializedName("股票代號")
    val id: String?,
    @SerializedName("股票名稱")
    val name: String?,
    @SerializedName("日期")
    val date: String?,
    @SerializedName("股票名稱1")
    val name2: String?,
    @SerializedName("產業指數代號")
    val industryId: String?,
    @SerializedName("上市上櫃")
    val goPublic: String?
)