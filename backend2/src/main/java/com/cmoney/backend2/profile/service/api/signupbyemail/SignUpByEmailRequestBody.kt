package com.cmoney.backend2.profile.service.api.signupbyemail

import com.google.gson.annotations.SerializedName

data class SignUpByEmailRequestBody(
    @SerializedName("Email")
    val email: String
)