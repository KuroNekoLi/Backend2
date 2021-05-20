package com.cmoney.backend2.chat.service.api.getsubjectallrequests.response

import com.google.gson.annotations.SerializedName

data class AuthorizationRequestResponseBody(
    @SerializedName("role")
    val role: String?,
    @SerializedName("userId")
    val userId: Long?
)