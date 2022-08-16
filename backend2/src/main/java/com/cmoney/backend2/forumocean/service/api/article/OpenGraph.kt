package com.cmoney.backend2.forumocean.service.api.article

import com.google.gson.annotations.SerializedName

data class OpenGraph(
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("siteName")
    val siteName: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)
