package com.cmoney.backend2.tickdata.service.api.getkchartdata

import com.google.gson.annotations.SerializedName

data class KDataItem(
    @SerializedName("KT")
    val time: Long?,
    @SerializedName("OP")
    val openPrice: Double?,
    @SerializedName("CP")
    val closePrice: Double?,
    @SerializedName("HP")
    val highestPrice: Double?,
    @SerializedName("LP")
    val lowestPrice: Double?,
    @SerializedName("TQ")
    val totalQuantity: Long?
)