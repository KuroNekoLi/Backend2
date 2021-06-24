package com.cmoney.backend2.profile.service.api.convertguestbyphone

import com.google.gson.annotations.SerializedName

data class ConvertGuestBySmsRequestBody(
    @SerializedName("Cellphone")
    val account: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Password")
    val newPassword: String
)