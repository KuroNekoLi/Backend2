package com.cmoney.backend2.common.service.api.getstockrssarticleswithfiltertype


import com.google.gson.annotations.SerializedName

data class StockNews(
    @SerializedName("HasLiked")
    val hasLiked: Boolean?,
    @SerializedName("Id")
    val id: String?,
    @SerializedName("LikeCount")
    val likeCount: Int?,
    @SerializedName("NewsTime")
    val newsTime: Long?,
    @SerializedName("RetweetCount")
    val reTweetCount: Int?,
    @SerializedName("SourceName")
    val sourceName: String?,
    @SerializedName("Tags")
    val tags: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Url")
    val url: String?,
    @SerializedName("ViewCount")
    val viewCount: String?
)