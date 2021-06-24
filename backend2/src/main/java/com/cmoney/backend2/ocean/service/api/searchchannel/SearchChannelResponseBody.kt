package com.cmoney.backend2.ocean.service.api.searchchannel
import com.google.gson.annotations.SerializedName


data class SearchChannelResponseBody(
    @SerializedName("Clubs")
    val clubs: List<Club>?
)