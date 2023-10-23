package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.google.gson.annotations.SerializedName

/**
 * 取得聊天室看板文章回傳物件
 *
 * @property articles 文章集合
 * @property hasNext 是否還有文章
 * @property nextStartWeight 能查到下個文章的權重
 */
data class GetChatGroupArticlesResponseBody(
    @SerializedName("articles")
    val articles: List<ChatGroupArticle>?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("nextStartWeight")
    val nextStartWeight: Long?
)
