package com.cmoney.backend2.ocean.service.api.getasklatestarticle

import com.google.gson.annotations.SerializedName

data class GetAskLatestArticleRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("BaseArticleId")
    val baseArticleId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("StockIdList")
    val stockIdList: List<String>
)