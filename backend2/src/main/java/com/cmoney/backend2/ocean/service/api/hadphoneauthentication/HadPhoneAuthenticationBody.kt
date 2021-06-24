package com.cmoney.backend2.ocean.service.api.hadphoneauthentication

import com.google.gson.annotations.SerializedName

data class HadPhoneAuthenticationBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ChannelIdList")
    val channelIdList: List<Long>,
    @SerializedName("Guid")
    val guid: String
)