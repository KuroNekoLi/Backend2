package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

enum class BullBear(val value : Int) {
    @SerializedName("0")
    None(0),
    @SerializedName("1")
    Bull(1),
    @SerializedName("-1")
    Bear(-1);
}