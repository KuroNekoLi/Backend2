package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.google.gson.annotations.SerializedName

/**
 *
 * @property articles 文章清單
 * @property hasNext 有下頁
 * @property nextStartWeight  取下一頁的Key
 */
data class GetGroupBoardArticlesResponse(
    @SerializedName("articles")
    val articles: List<GroupArticlesResponseBody>?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("nextStartWeight")
    val nextStartWeight: Long?
)
