package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.group

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat.GroupArticlesResponseBody
import com.google.gson.annotations.SerializedName


/**
 * 取得用戶不分社團所有非聊天室看板文章，排序為新到舊
 *
 * @property articles 文章列表
 * @property hasNext 是否還有下個文章
 * @property nextStartWeight 下一個文章權重
 */
data class GetGroupAllLatestArticlesResponseBody(
    @SerializedName("articles")
    val articles: List<GroupArticlesResponseBody>?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("nextStartWeight")
    val nextStartWeight: Long?
)
