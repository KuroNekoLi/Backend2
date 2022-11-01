package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.recommendations

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.google.gson.annotations.SerializedName

/**
 * Response object: GetRecommendation
 *
 * @property articles 文章清單
 * @property hasNext 有下頁
 * @property nextOffset 下頁長度
 *
 */
data class GetRecommendationResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponseBody.UnknownArticleResponseBody>,
    @SerializedName("hasNext")
    val hasNext: Boolean,
    @SerializedName("nextOffset")
    val nextOffset: Int
)
