package com.cmoney.backend2.mobileocean.service.api.getfanschannel

import com.google.gson.annotations.SerializedName

data class GetFansChannelResponse(
    @SerializedName("Channels")
    val channels: List<FansChannel>?
)
