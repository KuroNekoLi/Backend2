package com.cmoney.backend2.ocean.service.api.channelquestionsactivation

import com.google.gson.annotations.SerializedName

data class ChannelQuestionsActivationBody(
    @SerializedName("IsActive")
    val isActive : Boolean,
    @SerializedName("ChannelId")
    val channelId : Long,
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)