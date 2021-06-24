package com.cmoney.backend2.ocean.service.api.gettopicarticles

import com.google.gson.annotations.SerializedName

data class GetTopicArticlesRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Topic")
    val topic: String,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("BaseArticleId")
    val baseArticleId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int
)