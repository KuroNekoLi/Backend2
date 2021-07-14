package com.cmoney.backend2.profile.service.api.changepassword

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestBody(
    @SerializedName("OldPassword")
    val oldPassword: String,
    @SerializedName("NewPassword")
    val newPassword: String
)
