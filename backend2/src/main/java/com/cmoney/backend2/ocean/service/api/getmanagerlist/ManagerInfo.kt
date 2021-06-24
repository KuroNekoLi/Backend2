package com.cmoney.backend2.ocean.service.api.getmanagerlist


import com.google.gson.annotations.SerializedName

data class ManagerInfo(
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ChannelName")
    val channelName: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Image")
    val image: String?
)