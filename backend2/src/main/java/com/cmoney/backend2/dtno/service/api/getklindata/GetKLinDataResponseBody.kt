package com.cmoney.backend2.dtno.service.api.getklindata

import com.google.gson.annotations.SerializedName

data class GetKLinDataResponseBody(

    /**
     * 服務回來資料表(table)的各個Title名稱
     */
    @SerializedName("Title")
    val title: List<String>?,

    /**
     * 服務回來資料表(table)的各個Row資料
     */
    @SerializedName("Data")
    val data: List<List<String>?>?
)