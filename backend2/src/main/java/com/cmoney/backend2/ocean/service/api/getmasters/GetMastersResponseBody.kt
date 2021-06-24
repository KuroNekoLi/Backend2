package com.cmoney.backend2.ocean.service.api.getmasters


import com.google.gson.annotations.SerializedName

data class GetMastersResponseBody(
    /**
     * 達人資料列表
     */
    @SerializedName("Masters")
    val masters: List<Master>?
)