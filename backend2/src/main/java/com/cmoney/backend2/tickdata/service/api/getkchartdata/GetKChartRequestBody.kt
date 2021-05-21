package com.cmoney.backend2.tickdata.service.api.getkchartdata

import com.google.gson.annotations.SerializedName

/**
 * @param date Long 取資料當天日期
 * @param key String 期貨代號
 * @param interval Int 幾分k
 * @param count Int 取得筆數(從最近的一筆開始給)
 */
data class GetKChartRequestBody(
    @SerializedName("date")
    val date: Long,
    @SerializedName("key")
    val key: String,
    @SerializedName("interval")
    val interval: Int,
    @SerializedName("count")
    val count: Int
)