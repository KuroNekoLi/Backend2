package com.cmoney.backend2.forumocean.service.api.variable.request.mediatype

import com.google.gson.annotations.SerializedName

enum class Type(val typeString : String) {
    @SerializedName("image")
    IMAGE("image"),
    @SerializedName("video")
    VIDEO("video"),
    @SerializedName("video/youtube")
    YOUTUBE("video/youtube"),
    @SerializedName("audio")
    AUDIO("audio"),
    @SerializedName("source")
    Source("source");
}