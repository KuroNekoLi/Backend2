package com.cmoney.backend2.chat.service.api.getuserprofile.response

import com.google.gson.annotations.SerializedName

data class NameIdentifier(
    @SerializedName("issuer")
    val issuer: String?,
    @SerializedName("subject")
    val subject: String?
)