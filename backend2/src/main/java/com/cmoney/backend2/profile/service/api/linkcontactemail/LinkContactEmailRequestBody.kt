package com.cmoney.backend2.profile.service.api.linkcontactemail

import com.google.gson.annotations.SerializedName

data class LinkContactEmailRequestBody(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Email")
    val email: String
)
