package com.cmoney.backend2.mobileocean.service.api.activefollow

import com.google.gson.annotations.SerializedName

data class ActiveFollowResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("Message")
    val message: String?
)