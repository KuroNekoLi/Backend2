package com.cmoney.backend2.customgroup.service.api.getcustomlist

import com.google.gson.annotations.SerializedName

data class CustomList(
    @SerializedName("Stocks")
    val stocks: List<String>?
)