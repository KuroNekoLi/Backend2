package com.cmoney.backend2.mobileocean.service.api.leavechannel

import com.google.gson.annotations.SerializedName

data class LeaveChannelResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)