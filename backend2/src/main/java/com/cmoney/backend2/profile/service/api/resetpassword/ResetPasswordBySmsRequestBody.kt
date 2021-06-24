package com.cmoney.backend2.profile.service.api.resetpassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordBySmsRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Cellphone")
    val cellphone: String,
    @SerializedName("NewPassword")
    val newPassword: String
)
