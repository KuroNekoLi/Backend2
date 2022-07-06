package com.cmoney.backend2.realtimeaftermarket.service.api.getstocksinindex

import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("Commkey")
    val commKey: String?,
    @SerializedName("CommName")
    val commName: String?,
)