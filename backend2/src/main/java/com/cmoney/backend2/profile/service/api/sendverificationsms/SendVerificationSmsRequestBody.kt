package com.cmoney.backend2.profile.service.api.sendverificationsms

import com.google.gson.annotations.SerializedName

data class SendVerificationSmsRequestBody(
    @SerializedName("Cellphone")
    val phone: String?
)
