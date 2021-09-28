package com.cmoney.backend2.userbehavior.service.api.common

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("info")
    val descriptions: Map<String, Any>?,
    @SerializedName("name")
    val name: String,
    @SerializedName("time")
    val time: Long
)