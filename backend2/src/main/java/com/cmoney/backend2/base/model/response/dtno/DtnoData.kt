package com.cmoney.backend2.base.model.response.dtno

import com.google.gson.annotations.SerializedName

/**
 * 沒有錯誤的Dtno物件
 *
 * @param title 服務回來資料表(table)的各個Title名稱
 * @param data 服務回來資料表(table)的各個Row資料
 */
data class DtnoData(
    @SerializedName("Title")
    val title: List<String>?,
    @SerializedName("Data")
    val data: List<List<String>?>?
)