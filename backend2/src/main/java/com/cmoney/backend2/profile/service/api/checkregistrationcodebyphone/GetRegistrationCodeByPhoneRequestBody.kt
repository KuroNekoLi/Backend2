package com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone

import com.google.gson.annotations.SerializedName

data class GetRegistrationCodeByPhoneRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Cellphone")
    val cellphone: String
)