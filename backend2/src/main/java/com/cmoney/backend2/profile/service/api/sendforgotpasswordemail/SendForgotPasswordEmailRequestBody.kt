package com.cmoney.backend2.profile.service.api.sendforgotpasswordemail

import com.google.gson.annotations.SerializedName

data class SendForgotPasswordEmailRequestBody(
    @SerializedName("Email")
    val email: String?
)