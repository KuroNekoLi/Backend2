package com.cmoney.backend2.profile.service.api.queryprofile

import com.google.gson.annotations.SerializedName

data class RawFacebook(
    @SerializedName("fbId")
    val id: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?
)