package com.cmoney.backend2.profile.service.api.sendverificationemail

import com.google.gson.annotations.SerializedName

data class SendVerificationEmailRequestBody(
    @SerializedName("Email")
    val email: String?
)
