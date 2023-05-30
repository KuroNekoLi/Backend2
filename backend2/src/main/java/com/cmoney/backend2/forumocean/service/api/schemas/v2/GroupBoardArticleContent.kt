package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的GroupBoardArticleContent
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class GroupBoardArticleContent(
    @SerializedName("commodityTags")
    var commodityTags: List<CommodityTag>?,
    @SerializedName("text")
    var text: String?,
    @SerializedName("multiMedia")
    var multiMedia: List<Media>?
)