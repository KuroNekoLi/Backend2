package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleContentV2
import com.google.gson.annotations.SerializedName

/**
 * 被回覆的文章
 *
 * @property id 訊息編號
 * @property creatorId 創建者編號
 * @property weight 用於查詢的權重
 * @property content 被回覆的文章內容
 * @property isDelete 是否已刪除, true 已刪除, false 未刪除
 * @property isUnsent 是否已收回, true 已收回, false 未收回
 */
data class ArticleReply(
    @SerializedName("messageId")
    val id: String?,
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("messageWeight")
    val weight: Long?,
    @SerializedName("content")
    val content: ArticleContentV2?,
    @SerializedName("isDelete")
    val isDelete: Boolean?,
    @SerializedName("isUnsend")
    val isUnsent: Boolean?
)
