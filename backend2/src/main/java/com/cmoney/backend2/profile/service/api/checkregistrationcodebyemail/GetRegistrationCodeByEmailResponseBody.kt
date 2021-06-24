package com.cmoney.backend2.profile.service.api.checkregistrationcodebyemail

import com.google.gson.annotations.SerializedName

data class GetRegistrationCodeByEmailResponseBody(
    @SerializedName("registrationCode")
    val registrationCode: String
)
