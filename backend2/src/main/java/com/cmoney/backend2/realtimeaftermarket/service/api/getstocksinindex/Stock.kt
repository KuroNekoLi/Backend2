package com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex

import com.google.gson.annotations.SerializedName

/**
 * @property commKey 股票代號
 * @property commName 股票名稱
 */
data class Stock(
    @SerializedName("Commkey")
    val commKey: String?,
    @SerializedName("CommName")
    val commName: String?,
)