package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的Media
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class Media(
    @SerializedName("id")
    var id: String?,
    @SerializedName("width")
    var width: Int?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("mediaType")
    var mediaType: String?,
    @SerializedName("url")
    var url: String?
)