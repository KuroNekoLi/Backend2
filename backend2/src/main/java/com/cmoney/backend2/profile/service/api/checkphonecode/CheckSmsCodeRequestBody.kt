package com.cmoney.backend2.profile.service.api.checkphonecode

import com.google.gson.annotations.SerializedName

class CheckSmsCodeRequestBody(
    @SerializedName("Cellphone")
    val cellphone: String,
    @SerializedName("Code")
    val code: String
)