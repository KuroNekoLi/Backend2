package com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub
import com.google.gson.annotations.SerializedName


data class GetFansListExcludeJoinedClubResponseBody(
    @SerializedName("Channels")
    val channels: List<Channel>?
)


