package com.cmoney.backend2.ocean.service.api.getCollectArticleList

import com.google.gson.annotations.SerializedName

data class GetCollectArticleLIstRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("SkipCount")
    val skipCount: Int,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("IsIncludeLimitedAskArticle")
    val isIncludeLimitedAskArticle: Boolean
)