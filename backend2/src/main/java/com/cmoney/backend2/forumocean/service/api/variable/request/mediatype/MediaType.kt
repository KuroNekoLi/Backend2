package com.cmoney.backend2.forumocean.service.api.variable.request.mediatype

import com.google.gson.annotations.SerializedName

data class MediaType(
    @SerializedName("mediaType")
    val type : Type,
    @SerializedName("url")
    val url : String
)