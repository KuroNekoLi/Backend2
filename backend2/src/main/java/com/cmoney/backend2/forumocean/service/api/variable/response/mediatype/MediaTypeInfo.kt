package com.cmoney.backend2.forumocean.service.api.variable.response.mediatype

import com.google.gson.annotations.SerializedName

data class MediaTypeInfo(
    @SerializedName("mediaType")
    val type : TypeInfo?,
    @SerializedName("url")
    val url : String?
)