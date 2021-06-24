package com.cmoney.backend2.mobileocean.service.api.getattentionchannel


import com.google.gson.annotations.SerializedName

data class GetAttentionChannelResponse(
    @SerializedName("Channels")
    val channels: List<AttentionChannel>?
)