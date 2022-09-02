package com.cmoney.backend2.forumocean.service.api.article

import com.google.gson.annotations.SerializedName

/**
 * OpenGraph
 *
 * @property description OpenGraph 描述
 * @property image 圖片url
 * @property siteName 網站名稱
 * @property title 標題
 * @property url 轉導向url
 */
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
