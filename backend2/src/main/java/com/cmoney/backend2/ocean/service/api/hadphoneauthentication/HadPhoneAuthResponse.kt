package com.cmoney.backend2.ocean.service.api.hadphoneauthentication
import com.google.gson.annotations.SerializedName

data class HadPhoneAuthResponse(
    @SerializedName("Data")
    val data: List<PhoneAuthentication?>?
)

data class PhoneAuthentication(
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("HadPhoneAuthentication")
    val hadPhoneAuthentication: Boolean?
)