package com.cmoney.backend2.ocean.service.api.channelquestionsactivation


import com.google.gson.annotations.SerializedName

data class ChannelQuestionsActivationResponse(
    @SerializedName("IsActive")
    val isActive: Boolean?
)