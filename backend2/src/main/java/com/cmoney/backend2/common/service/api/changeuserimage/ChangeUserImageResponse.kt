package com.cmoney.backend2.common.service.api.changeuserimage

import com.google.gson.annotations.SerializedName

data class ChangeUserImageResponse(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?,
    @SerializedName("NewImagePath")
    val imagePath : String?
)