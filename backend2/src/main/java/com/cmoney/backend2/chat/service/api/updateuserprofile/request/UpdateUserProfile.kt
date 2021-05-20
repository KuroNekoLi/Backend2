package com.cmoney.backend2.chat.service.api.updateuserprofile.request

import com.google.gson.annotations.SerializedName

data class UpdateUserProfile(
    @SerializedName("userName")
    val userName: String,

    @SerializedName("userImageUrl")
    val userImageUrl: String
)