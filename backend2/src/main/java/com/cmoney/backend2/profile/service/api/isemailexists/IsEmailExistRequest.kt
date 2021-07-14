package com.cmoney.backend2.profile.service.api.isemailexists

import com.google.gson.annotations.SerializedName

data class IsEmailExistRequest(
    @SerializedName("Email")
    val email: String
)
