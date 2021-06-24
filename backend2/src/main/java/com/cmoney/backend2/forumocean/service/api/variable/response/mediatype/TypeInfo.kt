package com.cmoney.backend2.forumocean.service.api.variable.response.mediatype

import com.google.gson.annotations.SerializedName

enum class TypeInfo(val typeString : String) {
    @SerializedName("image")
    IMAGE("image"),
    @SerializedName("video")
    VIDEO("video"),
    @SerializedName("video/youtube")
    YOUTUBE("video/youtube"),
    @SerializedName("audio")
    AUDIO("audio");
}