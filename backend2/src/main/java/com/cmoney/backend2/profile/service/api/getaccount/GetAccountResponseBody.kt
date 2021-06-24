package com.cmoney.backend2.profile.service.api.getaccount

import com.google.gson.annotations.SerializedName

data class GetAccountResponseBody(
    @SerializedName("cellphone")
    val cellphone: String?,
    @SerializedName("contactEmail")
    val contactEmail: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("facebook")
    val facebook: String?,
    @SerializedName("signupDate")
    val signUpDate: String?
)