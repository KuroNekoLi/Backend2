package com.cmoney.backend2.chat.service.api.getuserprofile.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponseBody(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("properties")
    val properties: Properties?
)