package com.cmoney.backend2.ocean.service.api.getstockpopulararticle
import com.google.gson.annotations.SerializedName


data class GetStockPopularArticleBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("FilterType")
    val filterType: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("IsIncludeLimitedAskArticle")
    val isIncludeLimitedAskArticle: Boolean,
    @SerializedName("SkipCount")
    val skipCount: Int,
    @SerializedName("StockIdList")
    val stockIdList: List<String>
)