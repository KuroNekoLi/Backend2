package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.cmoney.backend2.forumocean.service.api.schemas.v2.GroupBoardArticlePaginationBase
import com.google.gson.annotations.SerializedName

/**
 * 為更精確反映後端API格式，請改用[GroupBoardArticlePaginationBase]，或參照swagger另建類別於api.schema資料夾中
 * @property articles 文章清單
 * @property hasNext 有下頁
 * @property nextStartWeight  取下一頁的Key
 */
@Deprecated("請改用 api.schemas.v2.GroupBoardArticlePaginationBase")
data class GetGroupBoardArticlesResponse(
    @SerializedName("articles")
    val articles: List<GroupArticlesResponseBody>?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("nextStartWeight")
    val nextStartWeight: Long?
)
