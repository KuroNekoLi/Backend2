package com.cmoney.backend2.profile.service.api.linkemail

import com.google.gson.annotations.SerializedName

data class LinkEmailRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Email")
    val email: String
)
