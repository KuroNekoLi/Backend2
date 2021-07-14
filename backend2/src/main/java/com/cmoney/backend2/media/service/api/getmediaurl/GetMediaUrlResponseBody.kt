package com.cmoney.backend2.media.service.api.getmediaurl


import com.google.gson.annotations.SerializedName

data class GetMediaUrlResponseBody(
    @SerializedName("Url")
    val url: String?
)