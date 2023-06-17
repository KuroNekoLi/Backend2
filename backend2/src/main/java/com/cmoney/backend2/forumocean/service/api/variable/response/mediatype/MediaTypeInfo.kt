package com.cmoney.backend2.forumocean.service.api.variable.response.mediatype

import com.google.gson.annotations.SerializedName

data class MediaTypeInfo(
    @SerializedName("mediaType", alternate = ["MediaType"])
    val type : TypeInfo?,
    @SerializedName("url", alternate = ["Url"])
    val url : String?
)