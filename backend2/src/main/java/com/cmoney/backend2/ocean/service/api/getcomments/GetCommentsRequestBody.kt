package com.cmoney.backend2.ocean.service.api.getcomments
import com.google.gson.annotations.SerializedName


data class GetCommentsRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ArticleId")
    val articleId: Long,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("UpperBoundArticleId")
    val upperBoundArticleId: Long
)