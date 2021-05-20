package com.cmoney.backend2.chat.service.api.updateuserprofile.response

import com.google.gson.annotations.SerializedName

data class UpdateUserProfileResponseBody(
    @SerializedName("username")
    val userName: String?,

    @SerializedName("userimageurl")
    val userImageUrl: String?
)