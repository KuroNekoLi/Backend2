package com.cmoney.backend2.ocean.service.api.getchannellatestarticle
import com.google.gson.annotations.SerializedName

data class GetChannelLatestArticleBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("ArticleNeedInfo")
    val articleNeedInfo: Int,
    @SerializedName("BaseArticleId")
    val baseArticleId: Long,
    @SerializedName("ChannelIdList")
    val channelIdList: List<Long>,
    @SerializedName("FetchCount")
    val fetchCount: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("IsIncludeLimitedAskArticle")
    val isIncludeLimitedAskArticle: Boolean
)