package com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail

import com.google.gson.annotations.SerializedName

data class GetRegistrationCodeByEmailRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Email")
    val email: String
)
