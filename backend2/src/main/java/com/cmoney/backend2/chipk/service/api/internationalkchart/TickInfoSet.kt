package com.cmoney.backend2.chipk.service.api.internationalkchart


import com.google.gson.annotations.SerializedName

data class TickInfoSet(
    @SerializedName("TickInfoSet")
    val tickInfoSet: List<TickInfo?>?
)