package com.cmoney.backend2.clientconfiguration.service.api

import com.google.gson.annotations.SerializedName

data class ClientConfigKolInfo(
    @SerializedName("channelId")
    val channelId: Long?,
    @SerializedName("memberId")
    val memberId: Long?
)
