package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.recommendations

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBodyV2
import com.google.gson.annotations.SerializedName

/**
 * Response object: GetRecommendation
 *
 * @property articles 文章清單
 * @property hasNext 有下頁
 * @property nextOffset 取下一頁的Key
 *
 */
data class GetRecommendationResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponseBodyV2>,
    @SerializedName("hasNext")
    val hasNext: Boolean,
    @SerializedName("nextOffset")
    val nextOffset: Int
)
