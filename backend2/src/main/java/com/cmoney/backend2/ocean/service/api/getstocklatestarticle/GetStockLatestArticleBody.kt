package com.cmoney.backend2.ocean.service.api.getstocklatestarticle
import com.google.gson.annotations.SerializedName


data class GetStockLatestArticleBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("BaseArticleId")
    val baseArticleId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("FilterType")
    val filterType: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("IsIncludeLimitedAskArticle")
    val isIncludeLimitedAskArticle: Boolean,
    @SerializedName("StockIdList")
    val stockIdList: List<String>
)