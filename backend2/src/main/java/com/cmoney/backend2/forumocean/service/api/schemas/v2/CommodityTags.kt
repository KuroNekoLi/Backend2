package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的CommodityTags
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class CommodityTags(
    @SerializedName("type")
    var type: String?,
    @SerializedName("key")
    var key: String?,
    @SerializedName("bullOrBear")
    var bullOrBear: Int?
)