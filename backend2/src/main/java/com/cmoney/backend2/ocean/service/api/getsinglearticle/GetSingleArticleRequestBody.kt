package com.cmoney.backend2.ocean.service.api.getsinglearticle
import com.google.gson.annotations.SerializedName


data class GetSingleArticleRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ArticleId")
    val articleId: Long,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("Guid")
    val guid: String
)