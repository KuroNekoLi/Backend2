package com.cmoney.backend2.profile.service.api.linkphone

import com.google.gson.annotations.SerializedName

data class LinkPhoneRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Cellphone")
    val cellphone: String
)
