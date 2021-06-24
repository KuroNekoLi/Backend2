package com.cmoney.backend2.ocean.service.api.getmemberclubs

import com.google.gson.annotations.SerializedName

data class GetMemberClubsResponseBody(
    @SerializedName("Clubs")
    val clubs: List<Club?>?
)