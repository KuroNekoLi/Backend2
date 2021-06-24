package com.cmoney.backend2.ocean.service.api.getrecommendclubs

import com.google.gson.annotations.SerializedName

data class GetRecommendClubsResponseBody(
    @SerializedName("Clubs")
    val clubs: List<Club?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
)