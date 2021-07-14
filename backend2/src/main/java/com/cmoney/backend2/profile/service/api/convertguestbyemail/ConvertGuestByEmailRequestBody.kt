package com.cmoney.backend2.profile.service.api.convertguestbyemail

import com.google.gson.annotations.SerializedName

data class ConvertGuestByEmailRequestBody(
    @SerializedName("Email")
    val account: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Password")
    val newPassword: String
)