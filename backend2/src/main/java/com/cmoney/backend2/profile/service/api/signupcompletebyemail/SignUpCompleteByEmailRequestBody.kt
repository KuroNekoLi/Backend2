package com.cmoney.backend2.profile.service.api.signupcompletebyemail

import com.google.gson.annotations.SerializedName

data class SignUpCompleteByEmailRequestBody(
    @SerializedName("Email")
    val email: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Password")
    val password: String
)
