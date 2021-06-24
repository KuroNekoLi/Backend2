package com.cmoney.backend2.ocean.service.api.createclub

import com.google.gson.annotations.SerializedName

data class CreateClubResponseBody (
    @SerializedName("ChannelId")
    val channelId : Long?
)