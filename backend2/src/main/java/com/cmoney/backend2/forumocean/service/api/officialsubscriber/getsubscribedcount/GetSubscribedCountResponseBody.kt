package com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount

import com.google.gson.annotations.SerializedName

data class GetSubscribedCountResponseBody(
    @SerializedName("count")
    val count : Int?
)
