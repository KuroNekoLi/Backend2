package com.cmoney.backend2.customgroup.service.api.searchstocks


import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("marketType")
    val marketType: String?
)