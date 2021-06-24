package com.cmoney.backend2.mobileocean.service.api.getstockarticlelist

import com.google.gson.annotations.SerializedName

/**
 *被轉推的新聞文章(子物件)
 *
 * @property title 文章標題
 * @property sourceName 來源
 * @property id 文章ID
 * @property retweenCount 轉推數
 * @property likeCount 按讚數
 * @property url Url
 */
data class RetweetNewsArticle(
    @SerializedName("Title")
    val title: String,
    @SerializedName("SourceName")
    val sourceName: String,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("RetweetCount")
    val retweenCount: Int,
    @SerializedName("LikeCount")
    val likeCount: Int,
    @SerializedName("Url")
    val url: String
)