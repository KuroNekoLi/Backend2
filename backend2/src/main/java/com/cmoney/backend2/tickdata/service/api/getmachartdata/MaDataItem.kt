package com.cmoney.backend2.tickdata.service.api.getmachartdata

import com.google.gson.annotations.SerializedName

data class MaDataItem(
    @SerializedName("KT")
    val time: Long?,
    @SerializedName("MA20")
    val ma20: Double?,
    @SerializedName("MA60")
    val ma60: Double?,
    @SerializedName("MA240")
    val ma240: Double?
)