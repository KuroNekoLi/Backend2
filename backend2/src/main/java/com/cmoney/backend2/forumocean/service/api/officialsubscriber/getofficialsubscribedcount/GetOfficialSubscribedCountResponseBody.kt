package com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount

import com.google.gson.annotations.SerializedName

data class GetOfficialSubscribedCountResponseBody(
    @SerializedName("count")
    val count : Int?
)
