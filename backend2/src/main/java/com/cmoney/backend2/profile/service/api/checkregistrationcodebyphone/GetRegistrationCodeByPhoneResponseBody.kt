package com.cmoney.backend2.profile.service.api.checkregistrationcodebyphone

import com.google.gson.annotations.SerializedName

data class GetRegistrationCodeByPhoneResponseBody(
    @SerializedName("registrationCode")
    val registrationCode: String
)
