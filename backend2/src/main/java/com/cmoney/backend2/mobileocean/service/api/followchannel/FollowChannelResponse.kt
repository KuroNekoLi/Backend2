package com.cmoney.backend2.mobileocean.service.api.followchannel

import com.google.gson.annotations.SerializedName

data class FollowChannelResponse(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)