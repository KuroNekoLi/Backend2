package com.cmoney.backend2.forumocean.service.api.notify.getcount

import com.google.gson.annotations.SerializedName

data class GetNotifyCountResponseBody(
    @SerializedName("count")
    val count: Int?
)
