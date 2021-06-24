package com.cmoney.backend2.ocean.service.api.getstockandtopicarticles


import com.google.gson.annotations.SerializedName

data class GetStockAndTopicArticlesRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("StockId")
    val stockId: String,
    @SerializedName("Topic")
    val topic: String,
    @SerializedName("BaseArticleId")
    val baseArticleId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int
)