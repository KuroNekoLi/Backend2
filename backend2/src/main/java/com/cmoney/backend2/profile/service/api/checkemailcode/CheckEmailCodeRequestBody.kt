package com.cmoney.backend2.profile.service.api.checkemailcode

import com.google.gson.annotations.SerializedName

class CheckEmailCodeRequestBody(
    @SerializedName("Email")
    val email: String,
    @SerializedName("Code")
    val code: String
)