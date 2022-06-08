package com.cmoney.backend2.forumocean.service.api.article

import com.google.gson.annotations.SerializedName

data class ExchangeCount(
    @SerializedName("exchangedCount")
    val exchangeCount: Int?,
    @SerializedName("limit")
    val limit: Int?
)