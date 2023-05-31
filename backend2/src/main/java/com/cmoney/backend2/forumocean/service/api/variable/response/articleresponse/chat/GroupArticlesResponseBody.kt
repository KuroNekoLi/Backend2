package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.cmoney.backend2.forumocean.service.api.schemas.v2.GroupBoardArticle
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleContentV2
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBodyV2
import com.google.gson.annotations.SerializedName

/**
 * 為更精確反映後端API格式，請改用[GroupBoardArticle]，或參照swagger另建類別於api.schema資料夾中
 * Same as [ArticleResponseBodyV2] with additional field: [unsend].
 */
@Deprecated("請改用 api.schemas.v2.GroupBoardArticle")
data class GroupArticlesResponseBody(
    @SerializedName("id")
    val id: String?,
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("unsend")
    val unsend: Boolean?,
    @SerializedName("content")
    val articleContent: ArticleContentV2?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("modifyTime")
    val modifyTime: Long?,
    @SerializedName("myEmoji")
    val myEmoji: String?,
    @SerializedName("emojiCount")
    val emojiCount: Map<String, Int>?,
    @SerializedName("hasCollect")
    val collected: Boolean?,
    @SerializedName("collectedCount")
    val collectCount: Int?,
    @SerializedName("myComments")
    val myCommentIndex: List<Int>?,
    @SerializedName("commentCount")
    val commentCount: Int?,
    @SerializedName("@hash-shared")
    val shareCount: Int?,
    @SerializedName("hasInterest")
    val interested: Boolean?,
    @SerializedName("interestedCount")
    val interestCount: Int?,
    @SerializedName("rewardPoints")
    val rewardPoints: Int?,
    @SerializedName("donation")
    val donateCount: Int?,
    @SerializedName("@list-vote")
    val voteCount: Int?,
    @SerializedName("voteStatus")
    val voteStatus: Int?,
    @SerializedName("@value-reportCount")
    val totalReportCount: Int?,
    @SerializedName("hasReport")
    val hasReport: Boolean?,
    @SerializedName("isHidden")
    val isHidden: Boolean?,
    @SerializedName("isAnonymous")
    val anonymous: Boolean?,
    @SerializedName("authType")
    val authType: String?,
    @SerializedName("isPromotedArticle")
    val isPromotedArticle: Boolean?,
    @SerializedName("isPinnedPromotedArticle")
    val isPinnedPromotedArticle: Boolean?
)