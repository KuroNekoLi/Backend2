package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的GroupBoardArticleGroupBoardArticleContentArticlePaginationBase
 * 精簡化命名為GroupBoardArticlePaginationBase
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class GroupBoardArticlePaginationBase(
    @SerializedName("articles")
    var articles: List<GroupBoardArticle>?,
    @SerializedName("hasNext")
    var hasNext: Boolean?,
    @SerializedName("nextStartWeight")
    var nextStartWeight: Int?
)
