package com.cmoney.backend2.profile.service.api.signupcompletebyemail

import com.google.gson.annotations.SerializedName

data class SignUpCompleteByEmailResponseBody(
    @SerializedName("account")
    val account: String,
    @SerializedName("password")
    val password: String
)