package com.cmoney.backend2.customgroup2.service.api.createcustomgroup


import com.google.gson.annotations.SerializedName

data class CreateCustomGroupResponseBody(
    @SerializedName("id")
    val id: String?
)