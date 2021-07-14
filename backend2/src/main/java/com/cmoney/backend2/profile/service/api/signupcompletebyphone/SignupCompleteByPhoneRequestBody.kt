package com.cmoney.backend2.profile.service.api.signupcompletebyphone

import com.google.gson.annotations.SerializedName

data class SignupCompleteByPhoneRequestBody(
    @SerializedName("CellPhone")
    val cellphone: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Password")
    val password: String
)
