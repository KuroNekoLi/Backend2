package com.cmoney.backend2.profile.service.api.linkfacebook

import com.google.gson.annotations.SerializedName

data class LinkFacebookRequestBody(
    @SerializedName("Token")
    val token: String
)