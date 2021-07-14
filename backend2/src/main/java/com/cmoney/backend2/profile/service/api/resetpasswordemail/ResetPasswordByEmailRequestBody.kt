package com.cmoney.backend2.profile.service.api.resetpasswordemail

import com.google.gson.annotations.SerializedName

data class ResetPasswordByEmailRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("NewPassword")
    val newPassword: String
)
